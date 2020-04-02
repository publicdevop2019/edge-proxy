package com.hw.entity;

import com.hw.shared.BadRequestException;
import org.junit.Assert;
import org.junit.Test;

public class SecurityProfileTest {

    @Test
    public void url() {
        String testStr = "http://example.com:8111/*/**?search=mock&search2=mock2#frag1";
        SecurityProfile sp = new SecurityProfile();
        sp.convertToURIFromString(testStr);
        String url = sp.retrieveURIString();
        Assert.assertEquals(testStr, url);
    }

    @Test
    public void url_w_userInfo() {
        String testStr = "http://username:password@example.com:8111/mockPath?search=mock&search2=mock2#frag1";
        SecurityProfile sp = new SecurityProfile();
        sp.convertToURIFromString(testStr);
        String url = sp.retrieveURIString();
        Assert.assertEquals(testStr, url);
    }

    @Test(expected = BadRequestException.class)
    public void malformedUrl_no_port_delimiter() {
        String testStr = "http://example.com8111/mockPath?search=mock&search2=mock2#frag1";
        SecurityProfile sp = new SecurityProfile();
        sp.convertToURIFromString(testStr);
        String url = sp.retrieveURIString();
    }

    @Test(expected = BadRequestException.class)
    public void malformedUrl_extra_port_delimiter() {
        String testStr = "http://example.com::8111/mockPath?search=mock&search2=mock2#frag1";
        SecurityProfile sp = new SecurityProfile();
        sp.convertToURIFromString(testStr);
    }

}