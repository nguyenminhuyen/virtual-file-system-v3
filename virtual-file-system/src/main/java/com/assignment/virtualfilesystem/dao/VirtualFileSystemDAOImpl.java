package com.assignment.virtualfilesystem.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.assignment.virtualfilesystem.entity.Component;
import com.assignment.virtualfilesystem.entity.File;

@Repository
public class VirtualFileSystemDAOImpl implements VirtualFileSystemDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Component getComponentFromPath(String thePath) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		String sqlQuery =
				"WITH RECURSIVE compdata AS ( " +
					"(SELECT id, name, create_at, data, parent_id, CAST('root' as char(256)) as path " +
					"FROM Component " + 
					"WHERE id = 1) " +
					"UNION ALL " +
					"(SELECT this.id, this.name, this.create_at, this.data, this.parent_id, " +
					"cast(CONCAT(prior.path, '-', this.name) as char(256)) " +
					"FROM compdata prior " +
					"INNER JOIN Component this ON this.parent_id = prior.id) " +
					") " +
				"SELECT c.id " +
				"FROM compdata c WHERE c.path=:compPath";
		
		Query theQuery = currentSession.createSQLQuery(sqlQuery); 
		theQuery.setParameter("compPath", thePath);
		
		List<Integer> res = new ArrayList<>();
		res = theQuery.getResultList();
		System.out.println("the result of query: " + res.toString());
		
		if (res.size() != 1) {
			return null;
		}
		
		return currentSession.get(Component.class, res.get(0));
	}
	
	@Override
	public long getComponentSize(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sqlQuery = 
				"SELECT size " +
				"FROM component " +
				"where id=:componentId ";
		
		Query theQuery = currentSession.createSQLQuery(sqlQuery); 
		theQuery.setParameter("componentId", theId);
		
		List<BigInteger> size = new ArrayList<>();
		size = theQuery.getResultList();
		
		return size.get(0).longValue();
	}
	
	@Override
	public void updateComponentSize(int theId, long size) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("update Component " +
				 									"set size=:componentSize " + 
													"where id=:componentId");
		
		theQuery.setParameter("componentSize", size);
		theQuery.setParameter("componentId", theId);
		
		theQuery.executeUpdate();
		
	}
	
	@Override
	public void increaseComponentSize(int theId, long size) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sqlQuery = "update Component " +
						"set size=:componentSize + size " + 
						"where id=:componentId";
		
		Query theQuery = currentSession.createSQLQuery(sqlQuery); 
		
		theQuery.setParameter("componentSize", size);
		theQuery.setParameter("componentId", theId);
		
		theQuery.executeUpdate();
	}
	
	@Override
	public void decreaseComponentSize(int theId, long size) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("update Component " +
													"set size=size - :componentSize " + 
													"where id=:componentId");
		
		theQuery.setParameter("componentSize", size);
		theQuery.setParameter("componentId", theId);
		
		theQuery.executeUpdate();
	}
			
	@Override
	public void createComponent(Component theComponent) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.save(theComponent);

	}
	
	@Override
	public Component getComponent(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Component theComponent = currentSession.get(Component.class, theId);
		
		return theComponent;
	}

	@Override
	public String getData(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		File theFile = currentSession.get(File.class, theId);
		
		return theFile.getData();
	}

	@Override
	public List<Component> getDescendants(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		String sqlQuery = "select * from Component where parent_id=:componentId or id=:componentId";
		
		Query theQuery = currentSession.createSQLQuery(sqlQuery);
		theQuery.setParameter("componentId", theId);
		
		List<Component> directChilds = new ArrayList<>();
		directChilds = theQuery.getResultList();
		
		return directChilds;
	}

	@Override
	public void moveComponent(int theId, int destId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		String sqlQuery = "update Component "
						+ "set parent_id=:destComponentId "
						+ "where id=:componentId";
		
		Query theQuery = currentSession.createSQLQuery(sqlQuery);
		theQuery.setParameter("componentId", theId);
		theQuery.setParameter("destComponentId", destId);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void deleteComponent(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Component where id=:componentId");
		theQuery.setParameter("componentId", theId);
		
		theQuery.executeUpdate();
		
	}
	
	@Override
	public void updateComponent(int theId, String name, String data) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		
		if (data == null) {
			
			theQuery = currentSession.createQuery("update Component"
													+ "	set name=:componentName"
													+ " where id=:componentId");
			
		}
		
		else {
			
			theQuery = currentSession.createQuery("update Component"
													+ "	set name=:componentName, data=:componentData"
													+ " where id=:componentId");
			theQuery.setParameter("componentData", data);
		}
		
		theQuery.setParameter("componentId", theId);
		theQuery.setParameter("componentName", name);
		theQuery.executeUpdate();
		
	}

}
