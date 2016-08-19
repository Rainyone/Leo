package com.larva.utils;
public class IPEntry
{
  public String beginIp;
  public String endIp;
  public String country;
  public String area;

  public String toString()
  {
    return this.area + ";" + this.country + 
      ";" + "IP范围:" + this.beginIp + "-" + 
      this.endIp;
  }
}