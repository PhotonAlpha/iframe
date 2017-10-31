package ${entity.servicePackage};

import java.util.List;
import ${entity.servicePackage}.${entity.className};

public interface ${entity.className}Service{
	List<${entity.className}> findAll();
	
	Long insert(${entity.className} entity) throws Exception;
	boolean update(${entity.className} entity) throws Exception;
	
	void delete(${entity.className} entity) throws Exception;

}