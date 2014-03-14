package me.predatorray.jdbc;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;

public class ExtendedResultSetImplTest {

    @Test
    public void testGetNullableBooleanWithInt1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getBoolean(anyInt())).thenReturn(Boolean.TRUE);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Boolean boolVal = xResultSet.getNullableBoolean(0);

        verify(resultSet).getBoolean(anyInt());
        verify(resultSet).wasNull();
        Assert.assertNull(boolVal);
    }

    @Test
    public void testGetNullableBooleanWithInt2() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(false);
        when(resultSet.getBoolean(anyInt())).thenReturn(Boolean.TRUE);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Boolean boolVal = xResultSet.getNullableBoolean(0);

        verify(resultSet).getBoolean(anyInt());
        verify(resultSet).wasNull();
        Assert.assertTrue(boolVal);
    }

    @Test
    public void testGetNullableBooleanWithString1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getBoolean(anyInt())).thenReturn(Boolean.TRUE);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Boolean boolVal = xResultSet.getNullableBoolean("");

        verify(resultSet).getBoolean(anyString());
        verify(resultSet).wasNull();
        Assert.assertNull(boolVal);
    }

    @Test
    public void testGetNullableBooleanWithString2() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(false);
        when(resultSet.getBoolean(anyString())).thenReturn(Boolean.TRUE);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Boolean boolVal = xResultSet.getNullableBoolean("");

        verify(resultSet).getBoolean(anyString());
        verify(resultSet).wasNull();
        Assert.assertTrue(boolVal);
    }

    @Test
    public void testGetNullableByteWithInt1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getByte(anyInt())).thenReturn((byte) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Byte byteVal = xResultSet.getNullableByte(0);

        verify(resultSet).getByte(anyInt());
        verify(resultSet).wasNull();
        Assert.assertNull(byteVal);
    }

    @Test
    public void testGetNullableByteWithInt2() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(false);
        when(resultSet.getByte(anyInt())).thenReturn((byte) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Byte byteVal = xResultSet.getNullableByte(0);

        verify(resultSet).getByte(anyInt());
        verify(resultSet).wasNull();
        Assert.assertEquals(Byte.valueOf((byte) 1), byteVal);
    }

    @Test
    public void testGetNullableByteWithString1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getByte(anyString())).thenReturn((byte) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Byte byteVal = xResultSet.getNullableByte("");

        verify(resultSet).getByte(anyString());
        verify(resultSet).wasNull();
        Assert.assertNull(byteVal);
    }

    @Test
    public void testGetNullableByteWithString2() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(false);
        when(resultSet.getByte(anyString())).thenReturn((byte) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Byte byteVal = xResultSet.getNullableByte("");

        verify(resultSet).getByte(anyString());
        verify(resultSet).wasNull();
        Assert.assertEquals(Byte.valueOf((byte) 1), byteVal);
    }

    @Test
    public void testGetNullableShortWithInt1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getShort(anyInt())).thenReturn((short) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Short shortVal = xResultSet.getNullableShort(0);

        verify(resultSet).getShort(anyInt());
        verify(resultSet).wasNull();
        Assert.assertNull(shortVal);
    }

    @Test
    public void testGetNullableShortWithInt2() throws Exception {

    }

    @Test
    public void testGetNullableShortWithString1() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.wasNull()).thenReturn(true);
        when(resultSet.getShort(anyString())).thenReturn((short) 1);
        ExtendedResultSet xResultSet = new ExtendedResultSetImpl(resultSet);
        Short shortVal = xResultSet.getNullableShort("");

        verify(resultSet).getShort(anyString());
        verify(resultSet).wasNull();
        Assert.assertNull(shortVal);
    }

    @Test
    public void testGetNullableShortWithString2() throws Exception {

    }

    @Test
    public void testGetNullableInt1() throws Exception {

    }

    @Test
    public void testGetNullableInt2() throws Exception {

    }

    @Test
    public void testGetNullableLong1() throws Exception {

    }

    @Test
    public void testGetNullableLong2() throws Exception {

    }

    @Test
    public void testGetNullableFloat1() throws Exception {

    }

    @Test
    public void testGetNullableFloat2() throws Exception {

    }

    @Test
    public void testGetNullableDouble1() throws Exception {

    }

    @Test
    public void testGetNullableDouble2() throws Exception {

    }

    @Test
    public void testGetEnum1() throws Exception {

    }

    @Test
    public void testGetEnum2() throws Exception {

    }
}
