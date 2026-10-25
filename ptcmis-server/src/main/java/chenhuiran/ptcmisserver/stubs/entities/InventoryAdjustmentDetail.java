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

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
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
 */
public interface InventoryAdjustmentDetail extends Serializable {
  /**
   * <p>表名（ <code>inventory_adjustment_detail</code> ）。</p>
   * 库存调差明细
   */
  String TB_NAME = "inventory_adjustment_detail";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>idBottle</code>）的 JavaBean 属性名（<code>idBottle</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDBOTTLE = "idBottle";

  /**
   * <p>属性（<code>idBottle</code>）对应的数据库字段名（<code>id_bottle</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDBOTTLE = "id_bottle";

  /**
   * <p>属性（<code>idAdjustment</code>）的 JavaBean 属性名（<code>idAdjustment</code>）。</p>
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDADJUSTMENT = "idAdjustment";

  /**
   * <p>属性（<code>idAdjustment</code>）对应的数据库字段名（<code>id_adjustment</code>）。</p>
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDADJUSTMENT = "id_adjustment";

  /**
   * <p>属性（<code>bottles</code>）的 JavaBean 属性名（<code>bottles</code>）。</p>
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>bottles</code>）对应的数据库字段名（<code>bottles</code>）。</p>
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>plants</code>）的 JavaBean 属性名（<code>plants</code>）。</p>
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_PLANTS = "plants";

  /**
   * <p>属性（<code>plants</code>）对应的数据库字段名（<code>plants</code>）。</p>
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTS = "plants";

  /**
   * <p>主键ID。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdBottle();

  /**
   * <p>所属调差表ID。inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdAdjustment();

  /**
   * <p>调差瓶数。只能是 -1/0/1</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getBottles();

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getPlants();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
