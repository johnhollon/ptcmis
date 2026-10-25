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

package chenhuiran.ptcmisserver.interceptors;

import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.exceptions.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@SuppressWarnings("unused")
public class ExceptionsHandler {

  private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

  @ExceptionHandler(HttpException401.class)
  public ResponseEntity<String> handleHttpException401(HttpException401 e) {
    log.info("401未授权: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(HttpException403.class)
  public ResponseEntity<String> handleHttpException403(HttpException403 e) {
    log.info("403权限不足: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(e.getMessage());
  }

  @ExceptionHandler(HttpException404.class)
  public ResponseEntity<String> handleHttpException404(HttpException404 e) {
    log.warn("404资源不存在: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(HttpException500.class)
  public ResponseEntity<String> handleHttpException500(HttpException500 e) {
    String errorId = Id.g();
    log.info("500业务错误 [errorId={}]: {}", errorId, e.getMessage(), e);
    return ResponseEntity.internalServerError().body("500业务错误: %s".formatted(errorId));
  }

  @ExceptionHandler(HttpException.class)
  public ResponseEntity<String> handleHttpException(HttpException e) {
    String errorId = Id.g();
    log.info("Http业务异常 [errorId={}]: {}", errorId, e.getMessage(), e);
    return ResponseEntity.internalServerError().body("系统业务异常: %s".formatted(errorId));
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
    log.info("请求参数异常", e);
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    log.info("请求参数异常", e);
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(ErrorMessageException.class)
  public ResponseEntity<String> handleErrorMessageException(ErrorMessageException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    String errorId = Id.g();
    log.error("系统运行时异常 [errorId={}]", errorId, e);
    return ResponseEntity.internalServerError().body("系统运行时异常: %s".formatted(errorId));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    String errorId = Id.g();
    log.error("系统未知异常 [errorId={}]", errorId, e);
    return ResponseEntity.internalServerError().body("系统未知异常: %s".formatted(errorId));
  }

}
