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

package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentDetail} 接口。</p>
 * <p>库存调差明细</p>
 * <p>数据库表： <code>inventory_adjustment_detail</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentDetail}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class InventoryAdjustmentDetailDTO implements Cloneable, InventoryAdjustmentDetail {
  /**
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String idBottle;

  /**
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String idAdjustment;

  /**
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer bottles;

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer plants;

  /**
   * <p>无参构造方法。</p>
   * <p>库存调差明细</p>
   * <p>数据库表： <code>inventory_adjustment_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public InventoryAdjustmentDetailDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>库存调差明细</p>
   * <p>数据库表： <code>inventory_adjustment_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idAdjustment <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param bottles <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param plants <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   */
  public InventoryAdjustmentDetailDTO(String id, String idBottle, String idAdjustment,
      Integer bottles, Integer plants) {
    this.id = id;
    this.idBottle = idBottle;
    this.idAdjustment = idAdjustment;
    this.bottles = bottles;
    this.plants = plants;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>库存调差明细</p>
   * <p>数据库表： <code>inventory_adjustment_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public InventoryAdjustmentDetailDTO(InventoryAdjustmentDetail model) {
    this.id = model.getId();
    this.idBottle = model.getIdBottle();
    this.idAdjustment = model.getIdAdjustment();
    this.bottles = model.getBottles();
    this.plants = model.getPlants();
  }

  /**
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdBottle() {
    return idBottle;
  }

  /**
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdAdjustment() {
    return idAdjustment;
  }

  /**
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getBottles() {
    return bottles;
  }

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getPlants() {
    return plants;
  }

  /**
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdBottle(String idBottle) {
    this.idBottle = idBottle;
  }

  /**
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idAdjustment <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdAdjustment(String idAdjustment) {
    this.idAdjustment = idAdjustment;
  }

  /**
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param bottles <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setBottles(Integer bottles) {
    this.bottles = bottles;
  }

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param plants <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setPlants(Integer plants) {
    this.plants = plants;
  }

  /**
   * <p>通过 {@link #fetchId()} 的值计算哈希。</p>
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return fetchId().hashCode();
  }

  /**
   * 简单克隆。
   */
  @SuppressWarnings("UseSpecificCatch")
  public InventoryAdjustmentDetailDTO cloneSimple() {
    try{
      return (InventoryAdjustmentDetailDTO)clone();
    } catch (Throwable ignored) {
      return null;
    }
  }

  /**
   * <p>通过 {@link #fetchId()} 的值比较两个对象是否相等（支持传入字符串进行比较）。</p>
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("UseSpecificCatch")
  public boolean equals(Object other) {
    if(other == null) return false;
    if(other instanceof String s) return fetchId().equals(s);
    if(other instanceof InventoryAdjustmentDetailDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentDetail o) return fetchId().equals(o.fetchId());
    try{
      java.lang.reflect.Method fetchIdMethod = other.getClass().getMethod("fetchId");
      Object o = fetchIdMethod.invoke(other);
      if(o == null) return false;
      return fetchId().equals(o);
    } catch (Throwable ignored) {
      return false;
    }
  }
}
