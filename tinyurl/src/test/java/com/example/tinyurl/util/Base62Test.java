package com.example.tinyurl.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class Base62Test {
    private final Base62 base62;

    @Autowired
    public Base62Test(Base62 base62) {
        this.base62 = base62;
    }

    @Test
    public void testEncodeDecode() {
        //given
        long origin = 1L;

        //when
        String encodedString = base62.encode(origin);
        Long decode= base62.decode(encodedString);

        //then
        assertEquals(origin, decode);
    }


}