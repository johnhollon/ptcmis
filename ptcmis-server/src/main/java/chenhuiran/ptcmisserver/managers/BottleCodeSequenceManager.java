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

import chenhuiran.ptcmisserver.repository.BottleCodeSequenceDAO;
import chenhuiran.ptcmisserver.stubs.entities.BottleCodeSequence;
import chenhuiran.ptcmisserver.stubs.entities.BottleCodeSequencePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BottleCodeSequenceManager {

  public static final char[] ALPHABET = "ACDEFHIJKNPRTUVXY".toCharArray();
  public static final int RADIX = ALPHABET.length;
  public static final int CODE_LENGTH = 6;

  /**
   * 动态计算最大容量 (RADIX^CODE_LENGTH - 1)
   * 避免硬编码魔法数字，当修改字符集或长度时自动生效，无浮点精度隐患
   */
  public static final long MAX_CAPACITY = safePow() - 1;

  @Autowired
  private BottleCodeSequenceDAO bottleCodeSequenceDAO;

  @Transactional
  public String next() {
    // 悲观写锁获取当前计数器（存放的是当前待分配的序号）
    BottleCodeSequencePO counter = bottleCodeSequenceDAO.findWithLock();
    long current = counter.getCurrentValue();

    if (current > MAX_CAPACITY) {
      throw new IllegalStateException("瓶码序列计数器溢出：所有6位瓶码(AAAAAA‑YYYYYY)已经全部分配完毕");
    }

    // 计数器自增并立即 Flush 到数据库
    counter.setCurrentValue(current + 1);
    bottleCodeSequenceDAO.saveAndFlush(counter);

    // 将当前十进制数值转换为 17 进制字符编码
    return longToCode(current);
  }

  /**
   * peek 当前待分配的编码，不增加计数器；无锁只读
   */
  @Transactional(readOnly = true)
  public String queryForPeek() {
    BottleCodeSequence counter = bottleCodeSequenceDAO.findWithoutLock();
    return longToCode(counter.getCurrentValue());
  }

  private String longToCode(long value) {
    // 防御性校验：传入的数值绝不能超过当前码长能表达的最大值
    if (value < 0 || value > MAX_CAPACITY) {
      throw new IllegalArgumentException("数值 " + value + " 超过了 " + CODE_LENGTH + " 位编码的最大表达范围");
    }

    char[] buf = new char[CODE_LENGTH];
    for (int i = CODE_LENGTH - 1; i >= 0; i--) {
      buf[i] = ALPHABET[(int) (value % RADIX)];
      value /= RADIX;
    }
    return new String(buf);
  }

  /**
   * 纯整数安全求幂，防止 long 溢出与 Math.pow 浮点精度缺失
   */
  private static long safePow() {
    long result = 1;
    for (int i = 0; i < BottleCodeSequenceManager.CODE_LENGTH; i++) {
      if (result > Long.MAX_VALUE / (long) BottleCodeSequenceManager.RADIX) {
        throw new ArithmeticException("瓶码容量计算超出 Long.MAX_VALUE 范围");
      }
      result *= BottleCodeSequenceManager.RADIX;
    }
    return result;
  }
}
