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

import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.source.generator.Main;
import com.chenhuiran.source.generator.entities.Configuration;
import com.chenhuiran.source.generator.entities.Configuration.DbInfo;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig.ConstConfigs;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig.ConstConfigs.ConstConfig;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig.ConstConfigs.ConstConfig.SqlConfig;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig.OrmConfig;
import com.chenhuiran.source.generator.entities.Configuration.JavaConfig.OrmConfig.JpaConfig;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JavaStubsGeneratorUtil {

  private static final Path cachedFilePath = Paths.get("D:\\workspace\\ptcmis\\ptcmis-server\\sql-dll\\source-generator.yaml");

  public static void main(String[] args) {
    System.out.println("准备开始生成Java存根实体类。");
    System.out.println("请根据指引完成生成配置。");
    if (!checkCachedFile() && !generateConfigYamlFile()) {
      System.exit(0);
    }
    Main.generate(cachedFilePath.toFile().getAbsolutePath());
  }

  private static boolean checkCachedFile() {

    File configYamlFile;
    // 检查配置文件是否存在，是否是一个文件
    try {
      configYamlFile = cachedFilePath.toFile();
    } catch (Throwable e) {
      return false;
    }
    if (!configYamlFile.exists() || !configYamlFile.isFile()) {
      return false;
    }
    Configuration configuration;
    try {
      configuration = new ObjectMapper(new YAMLFactory()).readValue(configYamlFile, Configuration.class);
    } catch (IOException e) {
      return false;
    }
    if (configuration != null) {
      DbInfo dbInfo = configuration.getDb();
      if (configuration.getDb().getAesPrivateKeyPath() != null) {
        if (dbInfo == null) {
          return false;
        }
        // 配置信息校验。

        // url 不能为空或空串
        String url = dbInfo.getUrl();
        url = url == null ? null : url.trim();
        if (url == null || url.isEmpty()) {
          return false;
        }

        // username 不能为空或空串
        String username = dbInfo.getUsername();
        username = username == null ? null : username.trim();
        if (username == null || username.isEmpty()) {
          return false;
        }

        // password 不能为空或空串
        String password = dbInfo.getPassword();
        password = password == null ? null : password.trim();
        return password != null && !password.isEmpty();
      }
    }

    return true;
  }

  @SuppressWarnings("CallToPrintStackTrace")
  private static boolean generateConfigYamlFile() {
    Configuration configuration = new Configuration();
    try (Scanner sc = new Scanner(System.in)) {
      File configYamlFile;
      // 检查配置文件是否存在，是否是一个文件。如果已有此文件，则直接生成。
      try {
        configYamlFile = cachedFilePath.toFile();
      } catch (Throwable e) {
        configYamlFile = null;
      }
      if (configYamlFile != null && configYamlFile.exists() && configYamlFile.isFile()) {
        System.out.printf("是否按照上次生成方式（%s）重新生成（y/n，默认y）：%n", cachedFilePath.toFile().getAbsolutePath());
        String yes = sc.nextLine();
        if (!"n".equals(yes)) {
          return true;
        }
      }
      try {
        Files.createDirectories(cachedFilePath.getParent());
      } catch (IOException e) {
        System.err.printf("发生异常。%s%n", e.getMessage());
        e.printStackTrace();
        return false;
      }

      System.out.println("输入数据库类型（默认为 mysql）：");
      String database = sc.nextLine();
      database = database == null ? null : database.trim();
      database = database == null || database.isEmpty() ? "mysql" : database;

      System.out.println("输入数据库URL连接地址：");
      String url = sc.nextLine();
      url = url == null ? null : url.trim();
      if (url == null || url.isEmpty()) {
        return false;
      }
      System.out.println("输入数据库用户名：");
      String username = sc.nextLine();
      username = username == null ? null : username.trim();
      if (username == null || username.isEmpty()) {
        return false;
      }
      System.out.println("输入数据库密码：");
      String password = sc.nextLine();
      if (password == null || password.isEmpty()) {
        return false;
      }
      password = password.trim();
      DbInfo.DbInfoBuilder builder = DbInfo.builder();
      builder.database(database);
      builder.url(url);
      builder.username(username);
      builder.password(password);
      DbInfo dbInfo = builder.build();
      configuration.setDb(dbInfo);

      System.out.println("是否生成 mybatis ORM 相关代码（y/n，默认 y）：");
      String genMybatis = sc.nextLine();
      genMybatis = genMybatis == null ? null : genMybatis.trim();
      if (!"n".equalsIgnoreCase(genMybatis)) {
        if (configuration.getJava() == null) {
          configuration.setJava(new JavaConfig());
        }
        if (configuration.getJava().getOrm() == null) {
          configuration.getJava().setOrm(new OrmConfig());
        }
        // 本项目暂不需要生成 Mybatis 代码。
      }

      System.out.println("是否生成 jpa 实体类 相关代码（y/n，默认 y）：");
      String genJpa = sc.nextLine();
      genJpa = genJpa == null ? null : genJpa.trim();
      if (!"n".equalsIgnoreCase(genJpa)) {
        JpaConfig jpaConfig = new JpaConfig();
        if (configuration.getJava() == null) {
          configuration.setJava(new JavaConfig());
        }
        if (configuration.getJava().getOrm() == null) {
          configuration.getJava().setOrm(new OrmConfig());
        }
        configuration.getJava().getOrm().setJpa(jpaConfig);
        System.out.println("是否为所有表和视图生成实体类（y/n，默认 y）：");
        String genAll = sc.nextLine();
        genAll = genAll == null ? null : genAll.trim();
        if ("n".equalsIgnoreCase(genAll)) {
          System.out.println("请输入要生成的表或视图的名称，多个表或视图以英文逗号分隔：");
          String tables = sc.nextLine();
          if (tables != null && !tables.isEmpty()) {
            var list = Arrays.stream(tables.split(","))
              .map(String::trim)
              .filter(table -> !table.isEmpty())
              .collect(Collectors.toList());
            if (!list.isEmpty()) {
              jpaConfig.setIncludeTables(list);
            }
          }
        }
        System.out.println("请输入要排除掉的表或视图的名称，多个表或视图以英文逗号分隔：");
        String excludeTables = sc.nextLine();
        if (excludeTables != null && !excludeTables.isEmpty()) {
          var list = Arrays.stream(excludeTables.split(","))
            .map(String::trim)
            .filter(table -> !table.isEmpty())
            .collect(Collectors.toList());
          if (!list.isEmpty()) {
            jpaConfig.setExcludeTables(list);
          }
        }

        System.out.println("是否生成实体类存根接口（y/n，默认y）：");
        String genStub = sc.nextLine();
        genStub = genStub == null ? null : genStub.trim();
        boolean noStubs = false;
        if ("n".equalsIgnoreCase(genStub)) {
          noStubs = true;
          jpaConfig.setNoStubEntityInterfaces(true);
        } else {
          String stubSrc = null;
          while (stubSrc == null || stubSrc.isEmpty()) {
            System.out.println("生成的实体类存根接口存放的根路径（绝对路径）：");
            stubSrc = sc.nextLine();
            stubSrc = stubSrc == null ? null : stubSrc.trim();
          }
          jpaConfig.setStubEntityInterfacesSrc(stubSrc);
          String stubPackage = null;
          while (stubPackage == null || stubPackage.isEmpty()) {
            System.out.println("生成的实体类存根接口存放的包名：");
            stubPackage = sc.nextLine();
            stubPackage = stubPackage == null ? null : stubPackage.trim();
          }
          jpaConfig.setStubEntityInterfacesPackage(stubPackage);
        }

        System.out.println("是否生成 DTO 实体类（y/n，默认y）：");
        String genDto = sc.nextLine();
        genDto = genDto == null ? null : genDto.trim();
        if ("n".equalsIgnoreCase(genDto)) {
          jpaConfig.setNoDTOClass(true);
        } else {
          String dtoSrc;
          do {
            System.out.printf("生成 DTO 实体类存放的根路径（绝对路径%s）：%n", !noStubs ? "，如果不输入，则使用存根接口所在路径" : StringUtil.EMPTY);
            dtoSrc = sc.nextLine();
            dtoSrc = dtoSrc == null ? null : dtoSrc.trim();
            if (dtoSrc != null && !dtoSrc.isEmpty()) {
              jpaConfig.setDtoSrc(dtoSrc);
            }
          } while (noStubs && (dtoSrc == null || dtoSrc.isEmpty()));
          String dtoPackage;
          do {
            System.out.printf("生成 DTO 实体类存放的包名%s：%n", !noStubs ? "（如果不输入，则使用存根接口所在包名）" : StringUtil.EMPTY);
            dtoPackage = sc.nextLine();
            dtoPackage = dtoPackage == null ? null : dtoPackage.trim();
            if (dtoPackage != null && !dtoPackage.isEmpty()) {
              jpaConfig.setDtoPackage(dtoPackage);
            }
          } while (noStubs && (dtoPackage == null || dtoPackage.isEmpty()));
        }

        System.out.println("是否生成持久化 PO 实体类（y/n，默认y）：");
        String genPO = sc.nextLine();
        genPO = genPO == null ? null : genPO.trim();
        if ("n".equalsIgnoreCase(genPO)) {
          jpaConfig.setNoPOClass(true);
        } else {
          String poSrc = null;
          while (poSrc == null || poSrc.isEmpty()) {
            System.out.println("生成的持久化 PO 实体类存放的根路径（绝对路径）：");
            poSrc = sc.nextLine();
            poSrc = poSrc == null ? null : poSrc.trim();
          }
          jpaConfig.setPoSrc(poSrc);
          String poPackage = null;
          while (poPackage == null || poPackage.isEmpty()) {
            System.out.println("生成的持久化 PO 实体类存放的包名：");
            poPackage = sc.nextLine();
            poPackage = poPackage == null ? null : poPackage.trim();
          }
          jpaConfig.setPoPackage(poPackage);
          System.out.println("持久化 PO 实体类是否实现 org.springframework.data.domain.Persistable<ID> 接口（y/n，默认y）：");
          String noPOImplementsSpringPersistable = sc.nextLine();
          noPOImplementsSpringPersistable = noPOImplementsSpringPersistable == null ? null : noPOImplementsSpringPersistable.trim();
          if ("n".equalsIgnoreCase(noPOImplementsSpringPersistable)) {
            jpaConfig.setNoPOImplementsSpringPersistable(true);
          }

          System.out.printf("联合主键 PK 实体类存放的根路径（绝对路径%s）：%n", !noStubs ? "，如果不输入，则使用存根接口所在路径" : "，如果不输入，则使用持久化 PO 实体类所在路径");
          String pkSrc = sc.nextLine();
          pkSrc = pkSrc == null ? null : pkSrc.trim();
          if (pkSrc != null && !pkSrc.isEmpty()) {
            jpaConfig.setPkSrc(pkSrc);
          }
          System.out.printf("联合主键 PK 实体类的包名%s：%n", !noStubs ? "（如果不输入，则使用存根接口的包名）" : "（如果不输入，则使用持久化 PO 实体类的包名）");
          String pkPackage = sc.nextLine();
          pkPackage = pkPackage == null ? null : pkPackage.trim();
          if (pkPackage != null && !pkPackage.isEmpty()) {
            jpaConfig.setPkPackage(pkPackage);
          }
        }
      }

      System.out.println("是否生成 静态常量（接口） 相关代码（y/n，默认 y）：");
      String genConst = sc.nextLine();
      genConst = genConst == null ? null : genConst.trim();
      if (!"n".equalsIgnoreCase(genConst)) {
        ConstConfigs constConfigs = new ConstConfigs();
        constConfigs.setClasses(new ArrayList<>());
        if (configuration.getJava() == null) {
          configuration.setJava(new JavaConfig());
        }
        configuration.getJava().setConsts(constConfigs);

        String constSrc = null;
        while (constSrc == null || constSrc.isEmpty()) {
          System.out.println("生成静态常量（接口）存放的根路径（绝对路径）：");
          constSrc = sc.nextLine();
          constSrc = constSrc == null ? null : constSrc.trim();
        }
        constConfigs.setSrc(constSrc);

        int classIndex = 1;
        while (true) {
          if (classIndex > 1) {
            System.out.printf("%d. 是否继续生成第 %d 个类？（y/n，默认y）：%n", classIndex, classIndex);
            String continueGen = sc.nextLine();
            continueGen = continueGen == null ? null : continueGen.trim();
            if ("n".equalsIgnoreCase(continueGen)) {
              break;
            }
          }
          ConstConfig constConfig = new ConstConfig();
          constConfig.setSqls(new ArrayList<>());
          constConfigs.getClasses().add(constConfig);
          String qualifiedNameString = null;
          while (qualifiedNameString == null || qualifiedNameString.isEmpty()) {
            System.out.printf("%d. 生成静态常量类的全限定名（如 com.chenhuiran.source.generator.consts.ConstClass）：%n", classIndex);
            qualifiedNameString = sc.nextLine();
            qualifiedNameString = qualifiedNameString == null ? null : qualifiedNameString.trim();
          }
          constConfig.setClassQualifiedName(qualifiedNameString);

          int sqlIndex = 1;
          while (true) {
            if (sqlIndex > 1) {
              System.out.printf("%d.%d. 是否继续设置第 %d 个 SQL 语句？（y/n，默认y）：%n", classIndex, sqlIndex, sqlIndex);
              String continueGen = sc.nextLine();
              continueGen = continueGen == null ? null : continueGen.trim();
              if ("n".equalsIgnoreCase(continueGen)) {
                break;
              }
            }

            SqlConfig sqlConfig = new SqlConfig();
            constConfig.getSqls().add(sqlConfig);

            String sql = null;
            while (sql == null || sql.isEmpty()) {
              System.out.printf("%d.%d. 输入 SQL 语句：%n", classIndex, sqlIndex);
              sql = sc.nextLine();
              sql = sql == null ? null : sql.trim();
            }
            sqlConfig.setSql(sql);

            System.out.printf("%d.%d. Java类属性名是否使用大写形式？（y/n，默认y）：%n", classIndex, sqlIndex);
            String upperCase = sc.nextLine();
            upperCase = upperCase == null ? null : upperCase.trim();
            sqlConfig.setPropertyNameUseLowerCase("n".equalsIgnoreCase(upperCase));

            System.out.printf("%d.%d. Java类属性名前缀（可为 null）：%n", classIndex, sqlIndex);
            String propertyNamePrefix = sc.nextLine();
            propertyNamePrefix = propertyNamePrefix == null ? null : propertyNamePrefix.trim();
            sqlConfig.setPropertyNamePrefix(propertyNamePrefix == null || propertyNamePrefix.isEmpty() ? null : propertyNamePrefix);

            String propertyNameColumnNames = null;
            while (propertyNameColumnNames == null || propertyNameColumnNames.isEmpty()) {
              System.out.printf("%d.%d. Java类属性名由哪些字段的值拼接而成（数据库字段名集合，多个字段以英文逗号分隔）：%n", classIndex, sqlIndex);
              propertyNameColumnNames = sc.nextLine();
              propertyNameColumnNames = propertyNameColumnNames == null ? null : propertyNameColumnNames.trim();
            }
            sqlConfig.setPropertyNameColumnNames(Arrays.stream(propertyNameColumnNames.split(",")).map(String::trim).filter(name -> !name.isEmpty()).collect(Collectors.toList()));

            String propertyValueColumnNames = null;
            while (propertyValueColumnNames == null || propertyValueColumnNames.isEmpty()) {
              System.out.printf("%d.%d. Java类属性值由哪些字段的值拼接而成（数据库字段名集合，多个字段以英文逗号分隔）：%n", classIndex, sqlIndex);
              propertyValueColumnNames = sc.nextLine();
              propertyValueColumnNames = propertyValueColumnNames == null ? null : propertyValueColumnNames.trim();
            }
            sqlConfig.setPropertyValueColumnNames(Arrays.stream(propertyValueColumnNames.split(",")).map(String::trim).filter(name -> !name.isEmpty()).collect(Collectors.toList()));

            String propertyCommentColumnNames;
            System.out.printf("%d.%d. Java类属性注释由哪些字段的值拼接而成（数据库字段名集合，多个字段以英文逗号分隔）：%n", classIndex, sqlIndex);
            propertyCommentColumnNames = sc.nextLine();
            propertyCommentColumnNames = propertyCommentColumnNames == null ? StringUtil.EMPTY : propertyCommentColumnNames.trim();
            if (!propertyCommentColumnNames.isEmpty()) {
              sqlConfig.setPropertyCommentColumnNames(Arrays.stream(propertyCommentColumnNames.split(",")).map(String::trim).filter(name -> !name.isEmpty()).collect(Collectors.toList()));
            }

            sqlIndex++;
          }

          classIndex++;
        }
      }

      try {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.setDefaultPropertyInclusion(Include.NON_EMPTY);
        // writeValue方法，有两个参数，yaml文件的路径，要序列化的类型
        mapper.writeValue(cachedFilePath.toFile(), configuration);

      } catch (IOException e) {
        System.err.printf("生成文件 %s 失败。%n", cachedFilePath.toFile().getAbsolutePath());
        e.printStackTrace();
        return false;
      }
    } catch (Exception e) {
      System.err.printf("发生异常。%s%n", e.getMessage());
      e.printStackTrace();
      configuration = null;
    }
    return configuration != null;
  }
}
