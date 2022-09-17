package com.example.AEPB;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloTest {

    @Test
    void should_no_null() {

        Hello hello = new Hello();
        assertNotNull(hello);
    }
}
