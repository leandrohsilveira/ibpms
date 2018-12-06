<app-product-form>

    <form onsubmit={handleSubmit}>
        <fieldset>
            <div class={'field': true, 'error': !!errors.name}>
                <label for="name">Nome</label>
                <input type="text" name="name" ref="name" value={opts.product && opts.product.name}>
                <span if={!!errors.name} class="msg">{errors.name}</span>
            </div>

            <div class={'field': true, 'error': !!errors.price}>
                <label for="name">Preço</label>
                <input type="text" name="price" ref="price" class="input-money-mask" value={numeral((opts.product && opts.product.price) || 0).format('0,0.00')}>
                <span if={!!errors.price} class="msg">{errors.price}</span>
            </div>

            <div class="buttons">
                <button type="submit">{opts.product ? 'Atualizar' : 'Cadastrar'}</button>
                <button type="button" onclick={cancel} class="button button-clear">Cancelar</button>
            </div>
        </fieldset>
    </form>

    <style>
        :scope .field.error {
            color: red;
        }

        :scope .field.error > input {
            color: red;
            border-color: red;
            margin-bottom: 5px;
        }

        :scope .field > .msg {
            margin-bottom: 10px;
        }
    </style>

    <script>
        this.errors = {};
        this.maskedPriceRef = null;

        this.handleSubmit = (e) => {
            e.preventDefault();
            const errors = {};
            const name = this.refs.name.value;
            const price = this.maskedPriceRef.formatToNumber();
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

        this.on('mount', () => {
            this.maskedPriceRef = SimpleMaskMoney.setMask('.input-money-mask', window.ibpmsDefaultSimpleMaskMoneyConfig);
        });

    </script>
</app-product-form>