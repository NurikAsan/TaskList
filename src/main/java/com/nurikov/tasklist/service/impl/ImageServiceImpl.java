package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.task.TaskImage;
import com.nurikov.tasklist.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {


    @Override
    public String upload(TaskImage taskImage) {
        return null;
    }
}
