package com.vb.api.service;

import com.vb.api.view.CarView;

import java.util.List;

public interface IAdminService {

    List<CarView> findNotActiveCars();

}
