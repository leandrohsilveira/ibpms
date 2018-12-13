import 'riot-route/lib/tag';
import './product/products-route.tag';
import './product/product-create-route.tag';
import './product/product-update-route.tag';
import './product/product-delete-route.tag';

<app-routes>
    <router>
        <route path=""><app-products-route /></route>
        <route path="create"><app-product-create-route /></route>
        <route path="edit/*"><app-product-update-route /></route>
        <route path="delete/*"><app-product-delete-route /></route>
    </router>
</app-routes>