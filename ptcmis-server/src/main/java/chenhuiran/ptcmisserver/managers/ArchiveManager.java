/*
 * Copyright (C) 2026 陈慧然
 * (aka: Huiran Chen, chenhuiran, John Hollon, johnhollon)
 * ORCID: https://orcid.org/0009-0004-5899-6928
 * <chenhuiran@chenhuiran.com>
 * <johnhollon@johnhollon.com>
 *
 * This file is part of PTCMIS.
 *
 * PTCMIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PTCMIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package chenhuiran.ptcmisserver.managers;

import chenhuiran.ptcmisserver.repository.ArchiveDAO;
import chenhuiran.ptcmisserver.stubs.entities.ArchiveDTO;
import chenhuiran.ptcmisserver.stubs.entities.ArchivePO;
import com.chenhuiran.commons.lang.Id;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchiveManager {

  @Autowired
  private ArchiveDAO archiveDAO;

  @Autowired
  private TransactionTemplate transactionTemplate;

  private Path baseStoragePath;

  @PostConstruct
  public void init() {
    ApplicationHome home = new ApplicationHome(getClass());
    File homeDir = home.getDir();
    baseStoragePath = homeDir.toPath().resolve("archive").toAbsolutePath();
    if (!Files.exists(baseStoragePath)) {
      try {
        Files.createDirectories(baseStoragePath);
      } catch (IOException e) {
        throw new RuntimeException("附件存储目录创建失败:" + baseStoragePath, e);
      }
    }
  }

  public Path getPhysicalFilePath(String archiveId) {
    ArchivePO po = archiveDAO.findById(archiveId).orElse(null);
    if (po == null) {
      return null;
    }
    String relativeFileName = po.getServerFilePath();
    if (relativeFileName == null || relativeFileName.isBlank()) {
      return null;
    }
    return baseStoragePath.resolve(relativeFileName);
  }

  public void removeArchive(String archiveId) {
    ArchivePO po = archiveDAO.findById(archiveId).orElse(null);
    if (po == null) {
      return;
    }
    transactionTemplate.executeWithoutResult(status -> archiveDAO.deleteById(archiveId));
    Path absoluteFilePath = getPhysicalFilePath(archiveId);
    if (absoluteFilePath != null) {
      try {
        Files.deleteIfExists(absoluteFilePath);
      } catch (IOException ignored) {
      }
    }
  }

  public List<ArchiveDTO> queryByTableAndId(String tableName, String recordId) {
    return archiveDAO.findByTableNameAndRecordIdOrderByUploadTimeDesc(tableName, recordId)
      .stream().map(ArchiveDTO::new).toList();
  }


  public List<ArchiveDTO> queryByTableAndIdIn(String tableName, List<String> recordIdList) {
    return archiveDAO.findByTableNameAndRecordIdInOrderByUploadTimeDesc(tableName, recordIdList)
      .stream().map(ArchiveDTO::new).toList();
  }

  public void deleteByTableAndId(String tableName, String recordId) {
    var list = this.queryByTableAndId(tableName, recordId);
    if (list == null || list.isEmpty()) return;
    transactionTemplate.executeWithoutResult(status ->
      archiveDAO.deleteByTableNameAndRecordId(tableName, recordId)
    );
    cleanDiskFiles(list);
  }


  public void deleteByTableAndIdIn(String tableName, List<String> recordIdList) {
    var list = this.queryByTableAndIdIn(tableName, recordIdList);
    if (list == null || list.isEmpty()) return;
    transactionTemplate.executeWithoutResult(status ->
      archiveDAO.deleteByTableNameAndRecordIdIn(tableName, recordIdList)
    );
    cleanDiskFiles(list);
  }

  public ArchiveDTO findById(String archiveId) {
    return archiveDAO.findById(archiveId)
      .map(ArchiveDTO::new)
      .orElse(null);
  }

  public void uploadMulti(String tableName,
                          String recordId,
                          String bizType,
                          String description,
                          MultipartFile[] files,
                          String uploaderId,
                          String uploaderName) throws IOException {
    Assert.hasText(tableName, "tableName不能为空");
    Assert.hasText(recordId, "recordId不能为空");
    Assert.hasText(bizType, "bizType不能为空");
    Assert.hasText(uploaderId, "uploaderId不能为空");
    Assert.hasText(uploaderName, "uploaderName不能为空");

    List<ArchiveDTO> tempDtoList = new ArrayList<>();
    try {
      if (files != null) {
        for (MultipartFile file : files) {
          if (file.isEmpty()) {
            continue;
          }
          ArchiveDTO dto = writeFileToDisk(tableName, recordId, bizType, description, file, uploaderId, uploaderName);
          tempDtoList.add(dto);
        }
        if (!tempDtoList.isEmpty()) {
          transactionTemplate.executeWithoutResult(status -> persistArchiveMeta(tempDtoList));
        }
      }
    } catch (Exception ex) {
      cleanDiskFiles(tempDtoList);
      throw ex;
    }
  }

  private ArchiveDTO writeFileToDisk(String tableName,
                                     String recordId,
                                     String bizType,
                                     String description,
                                     MultipartFile file,
                                     String uploaderId,
                                     String uploaderName) throws IOException {
    String archiveId = Id.g();
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    long uploadTime = System.currentTimeMillis();

    String ext = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      ext = originalFilename.substring(originalFilename.lastIndexOf("."));
    }
    String diskFileName = archiveId + ext;
    Path targetAbsolutePath = baseStoragePath.resolve(diskFileName);

    file.transferTo(targetAbsolutePath);

    ArchiveDTO dto = new ArchiveDTO();
    dto.setId(archiveId);
    dto.setTableName(tableName);
    dto.setRecordId(recordId);
    dto.setBizType(bizType);
    dto.setDescription(description);
    dto.setFileName(originalFilename);
    dto.setContentType(contentType);
    dto.setServerFilePath(diskFileName);
    dto.setUploaderId(uploaderId);
    dto.setUploaderName(uploaderName);
    dto.setUploadTime(uploadTime);
    return dto;
  }

  private void persistArchiveMeta(List<ArchiveDTO> dtoList) {
    if (dtoList == null || dtoList.isEmpty()) return;

    List<ArchivePO> poList = dtoList.stream()
      .map(ArchivePO::new)
      .collect(Collectors.toList());
    archiveDAO.saveAll(poList);
  }

  private void cleanDiskFiles(List<ArchiveDTO> dtoList) {
    if (dtoList == null || dtoList.isEmpty()) {
      return;
    }
    for (ArchiveDTO dto : dtoList) {
      String relativeFileName = dto.getServerFilePath();
      if (relativeFileName == null || relativeFileName.isBlank()) {
        continue;
      }
      Path absolutePath = baseStoragePath.resolve(relativeFileName);
      try {
        if (Files.exists(absolutePath)) {
          Files.deleteIfExists(absolutePath);
        }
      } catch (IOException ignored) {
      }
    }
  }
}
