package ovaphlow.himawari.grpc;

import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ArchiveServiceImpl extends ArchiveGrpc.ArchiveImplBase {
    private static final Logger logger = Logger.getLogger(ArchiveServiceImpl.class.getName());

    @Override
    @SuppressWarnings("unchecked")
    public void search(ArchiveRequest req, StreamObserver<ArchiveReply> responseObserver) {
        System.out.println(req.getData());
        String resp = "";
        Gson gson = new Gson();

        try {
            Map<String, Object> body = gson.fromJson(req.getData(), Map.class);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "");
            map.put("content", body.get("keyword").toString());
            resp = gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("message", "gRPC服务器错误");
            resp = gson.toJson(map);
        }

        ArchiveReply reply = ArchiveReply.newBuilder().setData(resp).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}