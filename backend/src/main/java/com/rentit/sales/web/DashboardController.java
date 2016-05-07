package com.rentit.sales.web;

import com.rentit.inventory.application.service.InventoryService;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.service.SalesService;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    SalesService salesService;

    @RequestMapping(method = GET, path = "/catalog/form")
    public String getQueryForm(Model model) {
        model.addAttribute("catalogQuery", new CatalogQueryDTO());
        return "dashboard/catalog/query-form";
    }

    @RequestMapping(method = POST, path = "/catalog/query")
    public String queryPlantCatalog(Model model, CatalogQueryDTO query) {
        model.addAttribute("plants", inventoryService.findAvailablePlants(
                query.getName(),
                query.getRentalPeriod().getStartDate(),
                query.getRentalPeriod().getEndDate()));
        PurchaseOrderDTO po = new PurchaseOrderDTO();
        po.setRentalPeriod(query.getRentalPeriod());
        model.addAttribute("po", po);
        return "dashboard/catalog/query-result";
    }

    @RequestMapping(method = POST, path = "/orders")
    public String createPurchaseOrder(Model model, PurchaseOrderDTO purchaseOrderDTO) throws Exception {
//        purchaseOrderDTO = salesService.createPurchaseOrder(purchaseOrderDTO);
//        return "redirect:/dashboard/orders/" + purchaseOrderDTO.get_id();
        return  null;
    }

    @RequestMapping(method = GET, path = "/orders/{id}")
    public String showPurchaseOrder(Model model, @PathVariable Long id) {
        PurchaseOrderDTO po = salesService.findPurchaseOrder(PurchaseOrderID.of(id));
        model.addAttribute("po", po);
        return "dashboard/orders/show";
    }
}
