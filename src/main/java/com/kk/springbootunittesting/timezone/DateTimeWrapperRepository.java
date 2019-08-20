package com.kk.springbootunittesting.timezone;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kuriakose V
 * @since 20/08/19.
 */
public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, Long> {
}
