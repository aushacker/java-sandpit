package com.github.aushacker.sandpit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestOptional {

    private Optional<String> empty;
    private Optional<String> val;

    private String result;

    @BeforeEach
    public void setUp() {
        empty = Optional.empty();
        val = Optional.of("foo");
        result = null;
    }

    @Test
    public void testValueChecksWhenEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(empty.isPresent());
    }

    @Test
    public void testValueChecksWhenPresent() {
        assertFalse(val.isEmpty());
        assertTrue(val.isPresent());
    }

    @Test
    public void testGetWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> empty.get());
    }

    @Test
    public void testGetWhenPresent() {
        assertEquals("foo", val.get());
    }

    @Test
    public void testIfPresentWhenEmpty() {
        empty.ifPresent(v -> setResult(v));
        assertNull(result);
    }

    @Test
    public void testIfPresentWhenPresent() {
        val.ifPresent(v -> setResult(v));
        assertEquals("foo", result);
    }

    private void setResult(String result) {
        this.result = result;
    }
}
