<app-product-form>

    <form onsumit={submit}>
        <fieldset>
            <div class={'field': true, 'error': !!errors.name}>
                <label for="name">Nome</label>
                <input type="text" name="name" ref="name" value={opts.product && opts.product.name}>
                <span if={!!errors.name} class="msg">{errors.name}</span>
            </div>

            <div class={'field': true, 'error': !!errors.price}>
                <label for="name">Preço</label>
                <input type="text" name="price" ref="price" value={opts.product && opts.product.price}>
                <span if={!!errors.price} class="msg">{errors.price}</span>
            </div>

            <div class="buttons">
                <button type="submit">{opts.product ? 'Atualizar' : 'Cadastrar'}</button>
                <button type="button" onclick={cancel} class="button button-clear">Cancelar</button>
            </div>
        </fieldset>
    </form>

    <style>
        :scope .field.error > label {
            color: red;
        }

        :scope .field.error > input {
            border-color: red;
        }

        :scope .field.error > .msg {
            border-color: red;
        }
    </style>

    <script>
        this.errors = {};

        this.submit = (e) => {
            e.preventDefault();
            const errors = {};
            const name = this.refs.name.value;
            const price = this.refs.price.value;
            if(!name) errors['name'] = 'Campo obrigatório';
            if(!price) errors['price'] = 'Campo obrigatório';
            if(Object.keys(errors).length) {
                this.update({errors});
            } else if(typeof opts.onproductsubmit === 'function') {
                opts.onproductsubmit({
                    name,
                    price
                });
            }
        };

        this.cancel = () => {
            if(typeof opts.oncancel === 'function') {
                opts.oncancel();
            }
        };

    </script>
</app-product-form>