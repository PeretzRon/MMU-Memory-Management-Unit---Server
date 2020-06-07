package com.hit.server;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {

    private Socket socket;
    private CacheUnitController<T> controller;


    public HandleRequest(Socket s, CacheUnitController<T> controller) { // C'tor
        super();
        this.socket = s;
        this.controller = controller;
    }

    @Override
    public void run() {
        Request<DataModel<T>[]> requests;
        boolean isSucceeded = false;
        Scanner scanner = null;
        PrintWriter writer = null;
        String isSucceededStr = "";

        try {
            String req = "";
            scanner = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            Type ref = new TypeToken<Request<DataModel<T>[]>>() {
            }.getType();
            req = scanner.nextLine();
            if (req.equalsIgnoreCase("showStatistic")) {
                writer.print(controller.getCacheUnitService().getStatistics());
                writer.flush();

            } else { //loaded file update/get/delete

                JsonReader jReader = new JsonReader(new FileReader(req));
                requests = new Gson().fromJson(jReader, ref); // convert the json to string
                String action = requests.getHeaders().get("action");
                DataModel<T>[] dataModels = null;
                switch (action) {
                    case "GET":
                        dataModels = controller.get(requests.getBody());
                        isSucceeded = controller.getCacheUnitService().isGetSuccess();
                        if (dataModels != null && isSucceeded) {
                            isSucceeded = true;
                        }
                        isSucceededStr = String.valueOf(isSucceeded);
                        writer.println(isSucceededStr + requests.toString());
                        writer.flush();
                        break;
                    case "UPDATE":
                        isSucceeded = controller.update(requests.getBody());
                        isSucceededStr = String.valueOf(isSucceeded);
                        writer.println(isSucceededStr + requests.toString());
                        writer.flush();

                        break;
                    case "DELETE":
                        isSucceeded = controller.delete(requests.getBody());
                        isSucceededStr = String.valueOf(isSucceeded);
                        writer.println(isSucceededStr + requests.toString());
                        writer.flush();
                    default:
                        break;
                }
                controller.getCacheUnitService().getCacheUnit().getAlgo().print();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            writer.close();

        }

    }

}
