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

package chenhuiran.ptcmisserver.actions;

import chenhuiran.ptcmisserver.managers.ArchiveManager;
import chenhuiran.ptcmisserver.stubs.entities.ArchiveDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/archive")
public class ArchiveAction {

  @Autowired
  private ArchiveManager archiveManager;

  @GetMapping("/{archiveId}")
  public void download(@PathVariable String archiveId, HttpServletResponse response) throws IOException {
    Path physicalPath = archiveManager.getPhysicalFilePath(archiveId);
    if (physicalPath == null || !Files.exists(physicalPath)) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    ArchiveDTO dto = archiveManager.findById(archiveId);
    if (dto == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String encodedFileName = URLEncoder.encode(dto.getFileName(), StandardCharsets.UTF_8);
    String contentType = dto.getContentType() != null ? dto.getContentType() : "application/octet-stream";
    response.setContentType(contentType);

    String disposition;
    if (contentType.startsWith("image/")) {
      disposition = "inline; filename=\"" + encodedFileName + "\"";
    } else {
      disposition = "attachment; filename=\"" + encodedFileName + "\"";
    }
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, disposition);

    Files.copy(physicalPath, response.getOutputStream());
  }

  /**
   * 删除附件：同时删除数据库记录 + 磁盘文件
   * @param archiveId 附件主键
   */
  @DeleteMapping("/{archiveId}")
  public ResponseEntity<Void> delete(@PathVariable String archiveId) {
    ArchiveDTO dto = archiveManager.findById(archiveId);
    if (dto == null) {
      return ResponseEntity.notFound().build();
    }
    // manager内部：先删磁盘文件，再删数据库记录
    archiveManager.removeArchive(archiveId);
    return ResponseEntity.noContent().build();
  }
}
