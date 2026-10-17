package org.example.Model;

import java.time.LocalDateTime;

/**
 * Represents an immutable Bill record used for logging financial transactions.
 * * @param id        The unique identifier of the bill.
 * @param id_order  The identifier of the associated customer order.
 * @param id_client The identifier of the client who made the purchase.
 * @param amount    The total monetary amount of the bill.
 * @param issueDate The timestamp when the bill was generated.
 * * @author Bandila Briana
 * @version 1.0
 */

public record Bill(
        int id,
        int id_order,
        int id_client,
        double amount,
        LocalDateTime issueDate
) {
}
