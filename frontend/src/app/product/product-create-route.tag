import messageObservable from '../components/message';
import '../components/loading.tag';
import './product-form.tag';

<app-product-create-route>
    <h2>Cadastrar produto</h2>
    <app-product-form loading={loading} onproductsubmit={handleSubmit} oncancel={cancel} />
    <app-loading if={loading} />
    <script>
        this.loading = false;

        this.handleSubmit = (values) => {
            this.update({loading: true});
            fetch('/api/products', {
                method: 'POST',
                headers: new Headers({
                    'Content-Type': 'application/json'
                }),
                body: JSON.stringify(values)
            })
            .then(response => {
                if(response.status < 300) {
                    messageObservable.next('Produto cadastrado com sucesso');
                    route('/');
                } else {
                    response.json().then(error => messageObservable.next(error.message));
                }
            })
            .finally(() => {
                this.update({loading: false});
            })
        }

        this.cancel = () => {
            route('/');
        };
    </script>
</app-product-create-route>