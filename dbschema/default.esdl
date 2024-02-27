module default {

  abstract type AbstractType {
    required name : str;
    }

  type SubType extending AbstractType {
    required designation : str;
    }
  }
