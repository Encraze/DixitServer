/**
 * @author Igor Royd
 */
public class RequestHandler {
    public static Response process(Request request) {
        return request.getAction().process(request);
    }
}
