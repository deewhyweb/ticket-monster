package org.jboss.examples.ticketmonster.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SectionAllocationArrayType implements UserType<Long[][]> {
  @Override
  public int getSqlType() {
    return Types.ARRAY;
  }

  @Override
  public Class<Long[][]> returnedClass() {
    return Long[][].class;
  }

  /**
   * Compare two instances of the Java class mapped by this custom
   * type for persistence "equality", that is, equality of their
   * persistent state.
   *
   * @param x
   * @param y
   */
  @Override
  public boolean equals(Long[][] x, Long[][] y) {
    return false;
  }

  /**
   * Get a hash code for the given instance of the Java class mapped
   * by this custom type, consistent with the definition of
   * {@linkplain #equals(Object, Object) persistence "equality"} for
   * this custom type.
   *
   * @param x
   */
  @Override
  public int hashCode(Long[][] x) {
    return 0;
  }

  @Override
  public Long[][] nullSafeGet(ResultSet rs, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
    Array array = rs.getArray(i);
    return array != null? (Long[][]) array.getArray() : null;
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Long[][] longs, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
    if (st != null) {
      if (longs != null) {
        Array array = sharedSessionContractImplementor.getJdbcConnectionAccess().obtainConnection()
          .createArrayOf("bigint[]", longs);
        st.setArray(i, array);
      } else {
        st.setNull(i, Types.ARRAY);
      }
    }
  }

  @Override
  public Long[][] deepCopy(Long[][] longs) {
    ArrayList<Long[]> copy = new ArrayList<>();
    for (int i = 0; i < longs.length; i++) {
      copy.add((Long[])Arrays.asList(longs[i]).toArray());
    }
    return (Long[][])copy.toArray();
  }

  @Override
  public boolean isMutable() {
    return true;
  }

  @Override
  public Serializable disassemble(Long[][] longs) {
    return longs;
  }

  @Override
  public Long[][] assemble(Serializable serializable, Object o) {
    if (serializable instanceof Long[][]) {
      return (Long[][])serializable;
    }
    return new Long[0][];
  }
}
