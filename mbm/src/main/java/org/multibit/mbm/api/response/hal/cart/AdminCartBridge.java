package org.multibit.mbm.api.response.hal.cart;

import com.google.common.base.Optional;
import com.theoryinpractise.halbuilder.spi.Resource;
import org.multibit.mbm.api.response.hal.BaseBridge;
import org.multibit.mbm.db.dto.Cart;
import org.multibit.mbm.db.dto.User;

import javax.ws.rs.core.UriInfo;

/**
 * <p>Bridge to provide the following to {@link org.multibit.mbm.db.dto.User}:</p>
 * <ul>
 * <li>Creates a representation of a single {@link org.multibit.mbm.db.dto.User} update for an administrator</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class AdminCartBridge extends BaseBridge<Cart> {

  private final CustomerCartBridge customerCartBridge;

  /**
   * @param uriInfo   The {@link javax.ws.rs.core.UriInfo} containing the originating request information
   * @param principal An optional {@link org.multibit.mbm.db.dto.User} to provide a security principal
   */
  public AdminCartBridge(UriInfo uriInfo, Optional<User> principal) {
    super(uriInfo, principal);
    customerCartBridge = new CustomerCartBridge(uriInfo,principal);
  }

  public Resource toResource(Cart cart) {

    if (cart.getId() == null) {
      throw new IllegalArgumentException("Cannot respond with a transient Cart. Id is null.");
    }

    // Build on the Customer representation
    Resource userResource = customerCartBridge.toResource(cart)
      // Must use individual property entries due to collections
      .withProperty("id", cart.getId())
      // End of build
      ;

    return userResource;

  }

}