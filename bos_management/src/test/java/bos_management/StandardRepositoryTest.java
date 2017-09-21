package bos_management;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

	@Autowired
	private StandardRepository standardRepository; 
	
	@Test
	public void testFindByName() {
		System.out.println(standardRepository.findByName("30-40"));
	}
	
	@Test
	public void testQueryName() {
		System.out.println(standardRepository.queryName("30-40"));
	}
	
	@Test
	public void testQueryName2() {
		System.out.println(standardRepository.queryName2("30-40"));
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdateMinLength() {
		standardRepository.updateMinlength(1, 25);
	}
}
