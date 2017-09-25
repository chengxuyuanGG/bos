package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017-9-23.
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer> {

}
