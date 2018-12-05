<app-product-create-route>
    <app-product-form oncancel={cancel}></app-product-form>
    <script>
        this.cancel = () => {
            route('/');
        };
    </script>
</app-product-create-route>