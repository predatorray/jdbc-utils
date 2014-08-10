package me.predatorray.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ObjectMother {

    public static PreparedStatement getPreparedStatementInErrorState() {
        PreparedStatement ps = mock(PreparedStatement.class);
        try {
            doThrow(new SQLException()).when(ps).executeQuery();
            doThrow(new SQLException()).when(ps).executeUpdate();
            doThrow(new SQLException()).when(ps).setNull(anyInt(), anyInt());
            doThrow(new SQLException()).when(ps).setBoolean(anyInt(), anyBoolean());
            doThrow(new SQLException()).when(ps).setByte(anyInt(), anyByte());
            doThrow(new SQLException()).when(ps).setShort(anyInt(), anyShort());
            doThrow(new SQLException()).when(ps).setInt(anyInt(), anyInt());
            doThrow(new SQLException()).when(ps).setLong(anyInt(), anyLong());
            doThrow(new SQLException()).when(ps).setFloat(anyInt(), anyFloat());
            doThrow(new SQLException()).when(ps).setDouble(anyInt(), anyDouble());
            doThrow(new SQLException()).when(ps).setBigDecimal(anyInt(), any(BigDecimal.class));
            doThrow(new SQLException()).when(ps).setString(anyInt(), anyString());
            doThrow(new SQLException()).when(ps).setBytes(anyInt(), any(byte[].class));
            doThrow(new SQLException()).when(ps).setDate(anyInt(), any(Date.class));
            doThrow(new SQLException()).when(ps).setTime(anyInt(), any(Time.class));
            doThrow(new SQLException()).when(ps).setTimestamp(anyInt(), any(Timestamp.class));
            doThrow(new SQLException()).when(ps).setAsciiStream(anyInt(), any(InputStream.class), anyInt());
            doThrow(new SQLException()).when(ps).setUnicodeStream(anyInt(), any(InputStream.class), anyInt());
            doThrow(new SQLException()).when(ps).setBinaryStream(anyInt(), any(InputStream.class), anyInt());
            doThrow(new SQLException()).when(ps).clearParameters();
            doThrow(new SQLException()).when(ps).setObject(anyInt(), anyObject(), anyInt());
            doThrow(new SQLException()).when(ps).setObject(anyInt(), anyObject());
            doThrow(new SQLException()).when(ps).execute();
            doThrow(new SQLException()).when(ps).addBatch();
            doThrow(new SQLException()).when(ps).setCharacterStream(anyInt(), any(Reader.class), anyInt());
            doThrow(new SQLException()).when(ps).setRef(anyInt(), any(Ref.class));
            doThrow(new SQLException()).when(ps).setBlob(anyInt(), any(Blob.class));
            doThrow(new SQLException()).when(ps).setClob(anyInt(), any(Clob.class));
            doThrow(new SQLException()).when(ps).setArray(anyInt(), any(Array.class));
            doThrow(new SQLException()).when(ps).getMetaData();
            doThrow(new SQLException()).when(ps).setDate(anyInt(), any(Date.class), any(Calendar.class));
            doThrow(new SQLException()).when(ps).setTime(anyInt(), any(Time.class), any(Calendar.class));
            doThrow(new SQLException()).when(ps).setTimestamp(anyInt(), any(Timestamp.class), any(Calendar.class));
            doThrow(new SQLException()).when(ps).setNull(anyInt(), anyInt(), anyString());
            doThrow(new SQLException()).when(ps).setURL(anyInt(), any(URL.class));
            doThrow(new SQLException()).when(ps).getParameterMetaData();
            doThrow(new SQLException()).when(ps).setRowId(anyInt(), any(RowId.class));
            doThrow(new SQLException()).when(ps).setNString(anyInt(), anyString());
            doThrow(new SQLException()).when(ps).setNCharacterStream(anyInt(), any(Reader.class), anyLong());
            doThrow(new SQLException()).when(ps).setNClob(anyInt(), any(NClob.class));
            doThrow(new SQLException()).when(ps).setClob(anyInt(), any(Reader.class), anyLong());
            doThrow(new SQLException()).when(ps).setBlob(anyInt(), any(InputStream.class), anyLong());
            doThrow(new SQLException()).when(ps).setNClob(anyInt(), any(Reader.class), anyLong());
            doThrow(new SQLException()).when(ps).setSQLXML(anyInt(), any(SQLXML.class));
            doThrow(new SQLException()).when(ps).setObject(anyInt(), anyObject(), anyInt(), anyInt());
            doThrow(new SQLException()).when(ps).setAsciiStream(anyInt(), any(InputStream.class), anyLong());
            doThrow(new SQLException()).when(ps).setBinaryStream(anyInt(), any(InputStream.class), anyLong());
            doThrow(new SQLException()).when(ps).setCharacterStream(anyInt(), any(Reader.class), anyLong());
            doThrow(new SQLException()).when(ps).setAsciiStream(anyInt(), any(InputStream.class));
            doThrow(new SQLException()).when(ps).setBinaryStream(anyInt(), any(InputStream.class));
            doThrow(new SQLException()).when(ps).setCharacterStream(anyInt(), any(Reader.class));
            doThrow(new SQLException()).when(ps).setNCharacterStream(anyInt(), any(Reader.class));
            doThrow(new SQLException()).when(ps).setClob(anyInt(), any(Reader.class));
            doThrow(new SQLException()).when(ps).setBlob(anyInt(), any(InputStream.class));
            doThrow(new SQLException()).when(ps).setNClob(anyInt(), any(Reader.class));

            return ps;
        } catch (Exception e) {
            throw new IllegalStateException("Not expected here", e);
        }
    }
}
