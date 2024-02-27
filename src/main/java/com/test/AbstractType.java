package com.test;

import java.util.UUID;
import com.edgedb.driver.annotations.EdgeDBType;

@EdgeDBType
public abstract class AbstractType {
   public UUID id;
   public String name;
}
