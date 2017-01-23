package de.neozo.blockchain.rest;


import de.neozo.blockchain.domain.Transaction;
import de.neozo.blockchain.service.NodeService;
import de.neozo.blockchain.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Set;


@RestController()
@RequestMapping("transaction")
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;
    private final NodeService nodeService;

    @Autowired
    public TransactionController(TransactionService transactionService, NodeService nodeService) {
        this.transactionService = transactionService;
        this.nodeService = nodeService;
    }

    @RequestMapping
    Set<Transaction> getTransactionPool() {
        return transactionService.getTransactionPool();
    }


    @RequestMapping(method = RequestMethod.PUT)
    void addTransaction(@RequestBody Transaction transaction, @RequestParam(required = false) Boolean publish, HttpServletResponse response) {
        LOG.info("Add transaction " + Arrays.toString(transaction.getHash()));
        boolean success = transactionService.add(transaction);

        if (success) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

            if (publish != null && publish) {
                nodeService.broadcastPut("transaction", transaction);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

}