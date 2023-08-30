package com.example.test.controller.admin;

import com.example.test.annotation.AdminController;
import com.example.test.dto.OrgChartDTO;
import com.example.test.dto.RoleDTO;
import com.example.test.dto.response.SingleDataResponseDTO;
import com.example.test.entity.Role;
import com.example.test.service.ResponseService;
import com.example.test.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AdminController
@RequiredArgsConstructor
@Log4j2
public class RoleController {

    private final RoleService roleService;

    private final ResponseService responseService;

    @PostMapping("/role")
    public String addRole(RoleDTO.RequestRoleDTO requestRoleDTO, RedirectAttributes redirectAttributes) {
        Role addedRole = roleService.addRole(requestRoleDTO);

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/admin/roles";
    }// addRole

    @GetMapping("/roles")
    public String getRolesPage(Model model) {
        return "/admin/roles";
    }// Roles

    @GetMapping("/roles-orgchart")
    @ResponseBody
    public ResponseEntity<SingleDataResponseDTO> getRolesOrgChart() {
        OrgChartDTO roleOrgChart = roleService.makeOrgChartDTO();

        SingleDataResponseDTO<OrgChartDTO> responseDTO = responseService.getSingleSuccessResponseDTO(roleOrgChart);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }// getROlesHierarchy


}// RoleController





















