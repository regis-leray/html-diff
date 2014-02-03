package org.rayjars.diff;

import org.junit.Test;
import org.rayjars.diff.utils.TimeElapsedUtils;

import static org.junit.Assert.assertEquals;

public class ElapsedTimeUtilsTest {

	@Test
	public void shouldFormatTime() throws Exception{
		assertEquals("24s 878ms", TimeElapsedUtils.format(24878));
	}
	
	@Test
	public void shouldFormatTimeWithSeconds() throws Exception{
		assertEquals("01s", TimeElapsedUtils.format(1000));
	}
	
	@Test
	public void shouldFormatTimeWithMinutes() throws Exception{
		assertEquals("01m", TimeElapsedUtils.format(60000));
	}
	
	@Test
	public void shouldFormatTimeWithHours() throws Exception{
		assertEquals("24h", TimeElapsedUtils.format(86400000));
	}
}
