/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eServe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author carla
 */
public class file {

    public static String ver = "HTTP/1.1";
    public static String comps[] = {
        "\r\n"," 200 OK"," 404 Not Found","Content-Type: ","Content-Length: ",
        " 401 Unauthorized"," 501 Not Implemented"
    };

    public static byte[] response(String method, String path) throws IOException {
        File file;
        byte[] data = new byte[0];
        if ("/".equals(path)) { // redirect root path to index
            file = new File("index.html");
        } else { // remove first '/' to get files relatively
            file = new File(path.substring(1)); 
        }
        String head = "";
        if (file.exists()) { //check for file
            switch (method) {
                case "GET":
                    // Code for 'GET' method
                    data = Files.readAllBytes(Paths.get(file.getPath()));
                    head = ver+comps[1]+comps[0]+comps[3]+contType(file.getPath())+comps[4]+Integer.toString(data.length)+comps[0]+comps[0];
                    break;
                case "POST":
                    // Code for 'POST' method
                    head = ver+comps[5]+comps[0]+comps[0]; // not auth.
                    break;
                case "PUT":
                    // Code for 'PUT' method
                    head = ver+comps[5]+comps[0]+comps[0]; // not auth.
                    break;
                case "HEAD":
                    // Code for 'HEAD' method
                    head = ver+comps[1]+comps[0]+comps[3]+contType(file.getPath())+comps[4]+Integer.toString(data.length)+comps[0]+comps[0]; //only header
                    break;
                default:
                    head = ver+comps[6]+comps[0]+comps[0]; // not implemented
                    break;
            }
        } else { // Code if file is not found
            head = ver + comps[2] + comps[0] + comps[0]; // 404 error
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //create byte packet
        baos.write(head.getBytes());
        baos.write(data);
        byte[] res = baos.toByteArray();
        
        return res;
    }

    static String contType(String p) {
        String types[] = {"text/plain", "text/html", "text/css", "image/png","image/jpeg","image/gif","application/javascript"};
        String ext = p.substring(p.length() - 4);
        if ("html".equals(ext)) return types[1]+comps[0];
        if (".css".equals(ext)) return types[2]+comps[0];
        if (".png".equals(ext)) return types[3]+comps[0];
        if (".js".equals(ext.substring(1))) return types[6]+comps[0];
        if ("jpeg".equals(ext)) return types[4] + comps[0];
        if (".gif".equals(ext)) return types[5]+comps[0];
        return types[0] + comps[0];
    }
}
