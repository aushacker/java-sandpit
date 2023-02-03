package com.github.aushacker.sandpit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestOptional implements Supplier<Optional<String>> {

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
        empty.ifPresent(v -> setResult(v)); // setResult should not be called
        assertNull(result);
    }

    @Test
    public void testIfPresentWhenPresent() {
        val.ifPresent(v -> setResult(v));
        assertEquals("foo", result);
    }

    @Test
    public void testFilterWhenPresent() {
        Optional<String> filtered = val.filter(v -> "foo".equals(v));
        assertEquals("foo", filtered.get());
    }

    @Test
    public void testOrWhenEmpty() {
        assertEquals("bar", empty.or(this).get());
    }

    @Test
    public void testOrWhenPresent() {
        assertEquals("foo", val.or(this).get());
    }

    @Test
    public void testOrElseWhenEmpty() {
        assertEquals("bar", empty.orElse("bar"));
    }

    @Test
    public void testOrElseWhenPresent() {
        assertEquals("foo", val.orElse("bar"));
    }

    @Test
    public void testOrElseGetWhenEmpty() {
        assertEquals("helper", empty.orElseGet(new SupplyHelper()));
    }

    @Test
    public void testOrElseGetWhenPresent() {
        assertEquals("foo", val.orElseGet(new SupplyHelper()));
    }

    @Test
    public void testOrElseThrowWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> empty.orElseThrow());
    }

    @Test
    public void testOrElseThrowWhenPresent() {
        assertEquals("foo", val.orElseThrow());
    }

    public Optional<String> get() {
        return Optional.of("bar");
    }

    private void setResult(String result) {
        this.result = result;
    }

    static class SupplyHelper implements Supplier<String> {
        public String get() {
            return "helper";
        }
    }
}
