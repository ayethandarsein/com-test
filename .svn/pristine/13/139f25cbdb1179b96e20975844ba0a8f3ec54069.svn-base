/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service.imp;

import com.panu.competitor.information.dao.CapabilityDAO;
import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Capabilities;
import com.panu.competitor.information.spring.service.ICapabilityService;
import com.panu.competitor.information.spring.service.constant.NamedQueryConstant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HNDK
 */
@Named
public class CapabilityServiceImp implements ICapabilityService {

    @Autowired
    private CapabilityDAO capabilityDAO;

    @Override
    public List<Capabilities> findAllCapabilities() {
        return capabilityDAO.select(NamedQueryConstant.GET_CAPABILITIES);
    }

    @Override
    public void addCapability(Capabilities capability) throws CompetitorException {
        capabilityDAO.insert(capability);
    }

    @Override
    public void updateCapability(Capabilities updateCapability) throws CompetitorException {
        capabilityDAO.update(updateCapability);
    }

    @Override
    public Map<String, String> validateCapabilityInfo(Capabilities new_capability, List<Capabilities> capabilityList) {
        Map<String, String> validateIds = new HashMap<String,String>();
        capabilityList.stream().map((capability) -> {
            if (capability.getName().trim().equalsIgnoreCase(new_capability.getName().trim())) {
                validateIds.put("Name", "true");
            }
            return capability;
        }).filter((capability) -> (capability.getShortName().trim().equalsIgnoreCase(new_capability.getShortName().trim()))).forEach((_item) -> {
            validateIds.put("ShortName", "true");
        });
        return validateIds;
    }

    @Override
    public void deleteCapability(Capabilities deleteCapability) throws CompetitorException {
        capabilityDAO.delete(deleteCapability);
    }

    @Override
    public Capabilities getCapabilitesByCapabilyIds(String capabilityShortName) {
        return capabilityDAO.getCapabilitesByCapabilyIds(capabilityShortName);
    }

}
