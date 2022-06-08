package com.kor.java.ssg.dao;

abstract public class Dao {
		protected int lastId;
		
		Dao() {
			lastId = 0;
		}
		
		public int getLastId() {
			return lastId;
		}
		
		public int getNewId() {
			return lastId + 1;
		}
}
