CREATE MIGRATION m1wssuwylka55qthr2amhwrpyipghykbcfjwvimg3tcx4mzyr6rfjq
    ONTO initial
{
  CREATE ABSTRACT TYPE default::AbstractType {
      CREATE REQUIRED PROPERTY name: std::str;
  };
  CREATE TYPE default::SubType EXTENDING default::AbstractType {
      CREATE REQUIRED PROPERTY designation: std::str;
  };
};
