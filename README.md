## eServ - A Customizable HTTP Server

    I found myself in need of a customizable java http server and this is what I ended up with.
I thought it could be useful for other people so I've made the repo public.

### Basic Usage

    As this is a java library you only need to download the .jar file and include it as a 
library if you want to use it in it's most basic form.

    It's important to note that while the .jar file and NBProject are called eServ, the
library package name is 'eServe'

To start the server, simply run:
```
eServe.server.start(port,verb);
```
in main class of a project, where 'port' is an integer representing the port to run the server 
on, and 'verb' is a boolean that enables (true) or disables (false) verbose output.

### Modifying the Server

    Of course, the code is designed to be modified. The source code is in the form of a 
NetBeans Java Project with Ant.

    Most of the modifications you'll likely make are going to be in the 'file' class, however 
there are some commented out sections of the 'server' class, that enable some variables that
aren't used in the default form of the server.

#### Changing Response to Request Types

    In the 'file' class, there is a switch block that responds to different http requests. Here
is where you would alter existing response logic, add new http response logic, or create your 
own response logic, say if you need to host a site that sends a strange data type back to the
server.
````
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
````

components of an HTTP response are stored in the comps[] array as strings. 
````
public static String comps[] = {
    "\r\n"," 200 OK"," 404 Not Found","Content-Type: ","Content-Length: ",
    " 401 Unauthorized"," 501 Not Implemented"
};
````
They are concatenated together to create the text part of the HTTP response.

#### Changing Response to Content Type
    There are simpler ways to handle content-type in java, but this way gives much more flexibility
when an uncommon use-case must be implemented.

    Simply use the if statements and return a string based on the last 4 characters of a file path
to change or add content type responses
````
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
````

### Submitting Pull Requests

    I won't likely have much time to go over pull requests and decide if they should be implemented, 
but if I see a modification I think is really impressive/useful, then I might make time to check 
over it. Needless to say, if the pull requests don't have good descriptions, I won't give them a 
moment of my time.