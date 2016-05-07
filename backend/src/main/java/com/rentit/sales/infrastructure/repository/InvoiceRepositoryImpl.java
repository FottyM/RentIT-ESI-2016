package com.rentit.sales.infrastructure.repository;

import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.rentit.sales.domain.model.QInvoice;
import com.rentit.sales.domain.repository.CustomInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by akaiz on 5/5/2016.
 */
public class InvoiceRepositoryImpl implements CustomInvoiceRepository {
    @Autowired
    EntityManager em;
    QInvoice qInvoice= QInvoice.invoice;
    @Override
    @Transactional
    public boolean RemitanceAdviceRecieved(Long id) {
        new JPAUpdateClause(em,qInvoice)
                .set(qInvoice.remittancePaid,true).where(qInvoice.purchaseOrder.id.eq(id)).execute();
        return true;
    }
}
