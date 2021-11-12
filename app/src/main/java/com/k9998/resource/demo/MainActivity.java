package com.k9998.resource.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.k9998.resource.manage.IResourceAction;
import com.k9998.resource.manage.ResourceManage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResourceManage resourceManage = ResourceManage.getInstance();
        resourceManage.registerResourceType(new AssetsResourceType(), List.of(new IResourceAction<AssetsResource>() {

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public String getName() {
                return "文件详情";
            }

            @Override
            public String getDescribe() {
                return "文件详情";
            }

            @Override
            public boolean execute() {
                Log.d("1234", "" + getResource().getPath());
                return true;
            }
        }));
        for (String path : loadAssets("")) {
            resourceManage.addResource(new AssetsResource(path));
        }
    }

    List<String> loadAssets(String dir) {
        List<String> paths = new ArrayList<>();
        try {
            for (String subPath : getResources().getAssets().list(dir)) {
                if ("".equals(dir)) {
                    List<String> subPaths = loadAssets(subPath);
                    if (subPaths.isEmpty()) {
                        paths.add(subPath);
                    } else {
                        paths.addAll(subPaths);
                    }
                } else {
                    List<String> subPaths = loadAssets(dir + File.separator + subPath);
                    if (subPaths.isEmpty()) {
                        paths.add(dir + File.separator + subPath);
                    } else {
                        paths.addAll(subPaths);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

}