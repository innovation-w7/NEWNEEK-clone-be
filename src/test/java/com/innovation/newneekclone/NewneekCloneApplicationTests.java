package com.innovation.newneekclone;

import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.List;

@SpringBootTest
class NewneekCloneApplicationTests {


    @Test
    void contextLoads() {
    }
}
