package com.rosenzest.server.base.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 对HttpServletRequest进行重写<br/>
 * 1、用来接收application/json参数数据类型，即@RequestBody注解标注的参数,解决多次读取问题<br/>
 * 2、用来解决注解@RequestParam通过POST/PUT/DELETE/PATCH方法传递参数，解决多次读取问题 首先看一下springboot控制器三个注解：<br/>
 * 1、@PathVariable注解是REST风格url获取参数的方式，只能用在GET请求类型，通过getParameter获取参数<br/>
 * 2、@RequestParam注解支持GET和POST/PUT/DELETE/PATCH方式，Get方式通过getParameter获取参数和post方式通过getInputStream或getReader获取参数<br/>
 * 3、@RequestBody注解支持POST/PUT/DELETE/PATCH，可以通过getInputStream和getReader获取参数<br/>
 * 
 * @author fronttang
 * @date 2021/07/30
 */
public class BodyRepeatableReadRequestWrapper extends HttpServletRequestWrapper {

    // private ServletInputStream inputStream;

    public BodyRepeatableReadRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    //
    // /**
    // * @return
    // * @throws IOException
    // */
    // @Override
    // public ServletInputStream getInputStream() throws IOException {
    //
    // /**
    // * 每次调用此方法时将数据流中的数据读取出来，然后再回填到InputStream之中 </br>
    // * 解决通过@RequestBody和@RequestParam（POST方式）读取一次后控制器拿不到参数问题
    // */
    // if (this.getContentLength() < 1) {
    // return null;
    // }
    // if (this.inputStream == null) {
    // this.inputStream =
    // new ReadRepeatableInputStream(this.getRequest().getInputStream(), this.getRequest().getContentLength());
    // }
    // return inputStream;
    // }
    //
    // private class ReadRepeatableInputStream extends ServletInputStream {
    //
    // private final ByteArrayInputStream bi;
    //
    // private ReadRepeatableInputStream(ServletInputStream inputStream, int length) throws IOException {
    // if (length > 0) {
    // byte[] bytes = new byte[length];
    // IOUtils.read(inputStream, bytes, 0, length);
    // inputStream.read(bytes, 0, length);
    // bi = new ByteArrayInputStream(bytes, 0, length);
    // } else {
    // bi = null;
    // }
    // }
    //
    // @Override
    // public int read() throws IOException {
    // int bt = -1;
    // if (bi != null) {
    // bt = this.bi.read();
    // if (bt == -1) {
    // this.bi.reset();
    // }
    // }
    // return bt;
    // }
    //
    // @Override
    // public void reset() {
    // this.bi.reset();
    // }
    //
    // @Override
    // public boolean isFinished() {
    // return false;
    // }
    //
    // @Override
    // public boolean isReady() {
    // return false;
    // }
    //
    // @Override
    // public void setReadListener(ReadListener readListener) {
    //
    // }
    // }

}
