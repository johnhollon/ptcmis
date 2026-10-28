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

package chenhuiran.ptcmisserver.repository;

import chenhuiran.ptcmisserver.stubs.entities.TissueTaskPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TissueTaskDAO extends JpaRepository<TissueTaskPO, String>, JpaSpecificationExecutor<TissueTaskPO> {
  /**
   * 带关键字跨表检索 + 条件分页查询任务
   *
   * @param keyword     模糊关键字（品种、编码、创建人、质检/操作员姓名）
   * @param taskType    任务类型
   * @param taskStatus  任务状态
   * @param createStart 创建时间‑开始
   * @param createEnd   创建时间‑结束
   * @param startStart  任务开始时间‑开始
   * @param startEnd    任务开始时间‑结束
   * @param finishStart 完成时间‑开始
   * @param finishEnd   完成时间‑结束
   * @param pageable    分页对象
   */
  @Query(value = "SELECT DISTINCT t FROM TissueTaskPO t " +
    "WHERE (:keyword IS NULL OR :keyword = '' " +
    "OR t.variety LIKE :kw " +
    "OR t.creatorUserName LIKE :kw " +
    "OR t.codePrefix LIKE :kw " +
    "OR t.id IN (SELECT o.idTask FROM TissueTaskOppGroupPO o WHERE o.inspectorName LIKE :kw OR o.operatorName LIKE :kw OR o.assigneeInspectorName LIKE :kw OR o.assigneeOperatorName LIKE :kw) " +
    "OR t.id IN (SELECT b.idTask FROM TissueTaskBottlePO b WHERE b.inspectorName LIKE :kw OR b.operatorName LIKE :kw) " +
    "OR t.id IN (SELECT tu.idTask FROM TissueTaskUserPO tu JOIN UserPO u ON u.id = tu.idUser WHERE u.name LIKE :kw)" +
    ") " +
    "AND (:explantCollectionBatch IS NULL OR :explantCollectionBatch < 1 OR t.explantCollectionBatch = :explantCollectionBatch) " +
    "AND (:taskType IS NULL OR :taskType = '' OR t.taskType = :taskType) " +
    "AND (:taskStatus IS NULL OR :taskStatus = '' OR t.taskStatus = :taskStatus) " +
    "AND (:createStart IS NULL OR t.createTime >= :createStart) " +
    "AND (:createEnd IS NULL OR t.createTime <= :createEnd) " +
    "AND (:startStart IS NULL OR t.startTime >= :startStart) " +
    "AND (:startEnd IS NULL OR t.startTime <= :startEnd) " +
    "AND (:finishStart IS NULL OR t.finishTime >= :finishStart) " +
    "AND (:finishEnd IS NULL OR t.finishTime <= :finishEnd) ",
    countQuery = "SELECT COUNT(DISTINCT t) FROM TissueTaskPO t " +
      "WHERE (:keyword IS NULL OR :keyword = '' " +
      "OR t.variety LIKE :kw " +
      "OR t.creatorUserName LIKE :kw " +
      "OR t.codePrefix LIKE :kw " +
      "OR t.id IN (SELECT o.idTask FROM TissueTaskOppGroupPO o WHERE o.inspectorName LIKE :kw OR o.operatorName LIKE :kw OR o.assigneeInspectorName LIKE :kw OR o.assigneeOperatorName LIKE :kw) " +
      "OR t.id IN (SELECT b.idTask FROM TissueTaskBottlePO b WHERE b.inspectorName LIKE :kw OR b.operatorName LIKE :kw) " +
      "OR t.id IN (SELECT tu.idTask FROM TissueTaskUserPO tu JOIN UserPO u ON u.id = tu.idUser WHERE u.name LIKE :kw)" +
      ") " +
      "AND (:explantCollectionBatch IS NULL OR :explantCollectionBatch < 1 OR t.explantCollectionBatch = :explantCollectionBatch) " +
      "AND (:taskType IS NULL OR :taskType = '' OR t.taskType = :taskType) " +
      "AND (:taskStatus IS NULL OR :taskStatus = '' OR t.taskStatus = :taskStatus) " +
      "AND (:createStart IS NULL OR t.createTime >= :createStart) " +
      "AND (:createEnd IS NULL OR t.createTime <= :createEnd) " +
      "AND (:startStart IS NULL OR t.startTime >= :startStart) " +
      "AND (:startEnd IS NULL OR t.startTime <= :startEnd) " +
      "AND (:finishStart IS NULL OR t.finishTime >= :finishStart) " +
      "AND (:finishEnd IS NULL OR t.finishTime <= :finishEnd) "
  )
  Page<TissueTaskPO> findTaskPageByCondition(
    @Param("keyword") String keyword,
    @Param("kw") String likeKeyword,
    @Param("explantCollectionBatch") Integer explantCollectionBatch,
    @Param("taskType") String taskType,
    @Param("taskStatus") String taskStatus,
    @Param("createStart") Long createStart,
    @Param("createEnd") Long createEnd,
    @Param("startStart") Long startStart,
    @Param("startEnd") Long startEnd,
    @Param("finishStart") Long finishStart,
    @Param("finishEnd") Long finishEnd,
    Pageable pageable
  );
}
