package com.grini.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.grini.ecommerce.entity.Country;
import com.grini.ecommerce.entity.Product;
import com.grini.ecommerce.entity.ProductCategory;
import com.grini.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		
		HttpMethod[] theUnsportedAction = {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT};
		
		// disable http method for Class : PUT , POST , DELETE
		
		disableHttpMethods(Product.class ,config, theUnsportedAction);
		disableHttpMethods(ProductCategory.class ,config, theUnsportedAction);
		disableHttpMethods(Country.class ,config, theUnsportedAction);
		disableHttpMethods(State.class ,config, theUnsportedAction);
					
		
		// call an internal helper method
		exposeIds(config);
		
	}

	private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsportedAction) {
		config.getExposureConfiguration()
		.forDomainType(theClass)
		.withItemExposure((metdata, HttpMethod) -> HttpMethod.disable(theUnsportedAction))
		.withCollectionExposure((metdata, HttpMethod) -> HttpMethod.disable(theUnsportedAction));
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		
		// expose entity ids
		// 
		
		// - get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		// - create an array of entity types
		List<Class> entityClasses = new ArrayList<>();
		
		// get the entity type for the entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}		
		
		// - expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
	}

	
	
}
