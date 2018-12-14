import numeral from 'numeral';

import '../components/input.tag';

<app-product-form>

    <form onsubmit={handleSubmit}>
        <fieldset>
            <app-input 
                ref="code" 
                type="text" 
                name="code" 
                label="Código" 
                required
                initialvalue={opts.product && opts.product.code} 
            />
            <app-input 
                ref="name" 
                type="text" 
                name="name" 
                label="Nome"
                required
                initialvalue={opts.product && opts.product.name} 
            />
            <app-input 
                ref="price"
                type="text" 
                label="Preço" 
                name="price" 
                money
                required
                inputmode="numeric" 
                initialvalue={formatProductPrice(opts.product)} 
            />

            <div class="buttons">
                <button disabled={opts.loading} type="submit">{opts.product ? 'Atualizar' : 'Cadastrar'}</button>
                <button type="button" onclick={cancel} class="button button-clear">Cancelar</button>
            </div>
        </fieldset>
    </form>

    <script>
        this.formatProductPrice = (product) => {
            return numeral((product && product.price) || 0).format('0,0.00')
        };

        this.isFormValid = () => {
            return this.refs.code.valid && this.refs.name.valid && this.refs.price.valid
        }

        this.handleSubmit = (e) => {
            e.preventDefault();
            if(!opts.loading) {
                this.refs.code.validate();
                this.refs.name.validate();
                this.refs.price.validate()
                if(this.isFormValid()) {
                    const code = this.refs.code.value;
                    const name = this.refs.name.value;
                    const price = +this.refs.price.value;
                    opts.onproductsubmit({
                        code,
                        name,
                        price
                    });
                }
            }
        };

        this.cancel = () => {
            if(typeof opts.oncancel === 'function') {
                opts.oncancel();
            }
        };

    </script>
</app-product-form>