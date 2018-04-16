package com.example.filter;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ApiFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;
    @Context
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
//        Method method = resourceInfo.getResourceMethod();
//        IgnoreFilter ignoreFilter = method.getAnnotation(IgnoreFilter.class);
//        if (ignoreFilter == null) {
//            Map<String, String[]> params = request.getParameterMap();
//            String[] access_tokens = params.get("");
//            String access_token = "";
//            if (access_tokens != null && access_tokens.length > 0) {
//                access_token = access_tokens[0];
//            }
//            System.out.println("api进行逻辑验证");
//            if (StringUtils.isEmpty(access_token)) {
//                requestContext.abortWith(Response
//                        .status(Response.Status.UNAUTHORIZED)
//                        .entity("验证失败").build());
//            }
//        }
    }

}
