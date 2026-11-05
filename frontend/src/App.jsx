import { useState, useEffect } from "react";

const API = "http://localhost:8080/api";

async function req(path, token, method = "GET", body) {
  const res = await fetch(API + path, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: "Bearer " + token } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!res.ok) throw new Error("Erro " + res.status);
  const texto = await res.text();
  return texto ? JSON.parse(texto) : null;
}

function Login({ onLogin }) {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const [perfil, setPerfil] = useState("CLIENTE");
  const [modo, setModo] = useState("login");
  const [msg, setMsg] = useState("");

  async function enviar() {
    try {
      if (modo === "cadastro") {
        await req("/auth/register", null, "POST", { login, senha, perfil });
        setMsg("Cadastrado! Agora entre.");
        setModo("login");
        return;
      }
      onLogin(await req("/auth/login", null, "POST", { login, senha }));
    } catch (e) {
      setMsg(e.message);
    }
  }

  return (
    <div style={{ maxWidth: 320, margin: "60px auto" }}>
      <h2>{modo === "login" ? "Entrar" : "Cadastrar"}</h2>
      <input placeholder="Login" value={login} onChange={(e) => setLogin(e.target.value)} />
      <input placeholder="Senha" type="password" value={senha} onChange={(e) => setSenha(e.target.value)} />
      {modo === "cadastro" && (
        <select value={perfil} onChange={(e) => setPerfil(e.target.value)}>
          <option value="CLIENTE">Cliente</option>
          <option value="AGENTE">Agente</option>
        </select>
      )}
      <button onClick={enviar}>{modo === "login" ? "Entrar" : "Cadastrar"}</button>
      <button onClick={() => setModo(modo === "login" ? "cadastro" : "login")}>
        {modo === "login" ? "Criar conta" : "Já tenho conta"}
      </button>
      <p>{msg}</p>
    </div>
  );
}

function Clientes({ token }) {
  const vazio = { rg: "", cpf: "", nome: "", endereco: "", profissao: "", empregador: "", valor: "" };
  const [lista, setLista] = useState([]);
  const [f, setF] = useState(vazio);
  const [editId, setEditId] = useState(null);
  const [erro, setErro] = useState("");

  const carregar = () => req("/clientes", token).then(setLista).catch((e) => setErro(e.message));
  useEffect(() => { carregar(); }, []);

  async function salvar() {
    try {
      const rendimentos = f.empregador
        ? [{ entidadeEmpregadora: f.empregador, valor: Number(f.valor) }]
        : [];
      const dados = {
        rg: f.rg, cpf: f.cpf, nome: f.nome, endereco: f.endereco, profissao: f.profissao, rendimentos,
      };
      if (editId) {
        await req("/clientes/" + editId, token, "PUT", dados);
      } else {
        await req("/clientes", token, "POST", dados);
      }
      cancelarEdicao();
      carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  function editar(c) {
    const r = c.rendimentos[0];
    setF({
      rg: c.rg, cpf: c.cpf, nome: c.nome, endereco: c.endereco || "", profissao: c.profissao || "",
      empregador: r ? r.entidadeEmpregadora : "", valor: r ? String(r.valor) : "",
    });
    setEditId(c.id);
  }

  function cancelarEdicao() {
    setF(vazio);
    setEditId(null);
    setErro("");
  }

  async function excluir(id) {
    try {
      await req("/clientes/" + id, token, "DELETE");
      carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  const campo = (k, p) => (
    <input placeholder={p} value={f[k]} onChange={(e) => setF({ ...f, [k]: e.target.value })} />
  );

  return (
    <div>
      <h3>{editId ? "Editando cliente #" + editId : "Clientes"}</h3>
      {campo("nome", "Nome")}{campo("rg", "RG")}{campo("cpf", "CPF")}
      {campo("endereco", "Endereço")}{campo("profissao", "Profissão")}
      {campo("empregador", "Empregador")}{campo("valor", "Rendimento")}
      <button onClick={salvar}>{editId ? "Atualizar" : "Salvar"}</button>
      {editId && <button onClick={cancelarEdicao}>Cancelar edição</button>}
      <p style={{ color: "red" }}>{erro}</p>
      <ul>
        {lista.map((c) => (
          <li key={c.id}>
            {c.nome} - {c.cpf} - {c.profissao}{" "}
            <button onClick={() => editar(c)}>Editar</button>
            <button onClick={() => excluir(c.id)}>Excluir</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

function Automoveis({ token }) {
  const vazio = { matricula: "", ano: "", marca: "", modelo: "", placa: "", tipoProprietario: "EMPRESA" };
  const [lista, setLista] = useState([]);
  const [f, setF] = useState(vazio);
  const [erro, setErro] = useState("");

  const carregar = () => req("/automoveis", token).then(setLista).catch((e) => setErro(e.message));
  useEffect(() => { carregar(); }, []);

  async function salvar() {
    try {
      await req("/automoveis", token, "POST", { ...f, ano: Number(f.ano) });
      setF(vazio);
      setErro("");
      carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  const campo = (k, p) => (
    <input placeholder={p} value={f[k]} onChange={(e) => setF({ ...f, [k]: e.target.value })} />
  );

  return (
    <div>
      <h3>Automóveis</h3>
      {campo("matricula", "Matrícula")}{campo("ano", "Ano")}{campo("marca", "Marca")}
      {campo("modelo", "Modelo")}{campo("placa", "Placa")}
      <select value={f.tipoProprietario} onChange={(e) => setF({ ...f, tipoProprietario: e.target.value })}>
        <option>CLIENTE</option><option>EMPRESA</option><option>BANCO</option>
      </select>
      <button onClick={salvar}>Salvar</button>
      <p style={{ color: "red" }}>{erro}</p>
      <ul>
        {lista.map((a) => (
          <li key={a.id}>{a.marca} {a.modelo} ({a.ano}) - {a.placa} - {a.tipoProprietario}</li>
        ))}
      </ul>
    </div>
  );
}

function Pedidos({ token, perfil }) {
  const [pedidos, setPedidos] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [autos, setAutos] = useState([]);
  const [clienteId, setClienteId] = useState("");
  const [automovelId, setAutomovelId] = useState("");
  const [erro, setErro] = useState("");

  const carregar = () => {
    req("/pedidos", token).then(setPedidos).catch((e) => setErro(e.message));
    req("/clientes", token).then(setClientes).catch(() => {});
    req("/automoveis", token).then(setAutos).catch(() => {});
  };
  useEffect(() => { carregar(); }, []);

  async function acao(fn) {
    try {
      await fn();
      setErro("");
      carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  return (
    <div>
      <h3>Pedidos de aluguel</h3>
      <select value={clienteId} onChange={(e) => setClienteId(e.target.value)}>
        <option value="">Cliente</option>
        {clientes.map((c) => <option key={c.id} value={c.id}>{c.nome}</option>)}
      </select>
      <select value={automovelId} onChange={(e) => setAutomovelId(e.target.value)}>
        <option value="">Automóvel</option>
        {autos.map((a) => <option key={a.id} value={a.id}>{a.marca} {a.modelo}</option>)}
      </select>
      <button
        onClick={() => acao(() => req("/pedidos", token, "POST", {
          clienteId: Number(clienteId), automovelId: Number(automovelId),
        }))}
      >
        Criar pedido
      </button>
      <p style={{ color: "red" }}>{erro}</p>
      <ul>
        {pedidos.map((p) => (
          <li key={p.id}>
            #{p.id} - {p.cliente.nome} - {p.automovel.marca} {p.automovel.modelo} - <b>{p.status}</b>{" "}
            <button onClick={() => acao(() => req("/pedidos/" + p.id, token, "DELETE"))}>Cancelar</button>
            {perfil === "AGENTE" && (
              <>
                <button onClick={() => acao(() => req(`/pedidos/${p.id}/status?status=APROVADO`, token, "PATCH"))}>Aprovar</button>
                <button onClick={() => acao(() => req(`/pedidos/${p.id}/status?status=REJEITADO`, token, "PATCH"))}>Rejeitar</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default function App() {
  const [sessao, setSessao] = useState(() => {
    const salvo = localStorage.getItem("sessao");
    return salvo ? JSON.parse(salvo) : null;
  });
  const [aba, setAba] = useState("pedidos");

  function entrar(dados) {
    localStorage.setItem("sessao", JSON.stringify(dados));
    setSessao(dados);
  }

  function sair() {
    localStorage.removeItem("sessao");
    setSessao(null);
  }

  if (!sessao) return <Login onLogin={entrar} />;

  return (
    <div style={{ maxWidth: 800, margin: "20px auto" }}>
      <p>
        Logada como <b>{sessao.login}</b> ({sessao.perfil}) <button onClick={sair}>Sair</button>
      </p>
      <button onClick={() => setAba("clientes")}>Clientes</button>
      <button onClick={() => setAba("automoveis")}>Automóveis</button>
      <button onClick={() => setAba("pedidos")}>Pedidos</button>
      {aba === "clientes" && <Clientes token={sessao.token} />}
      {aba === "automoveis" && <Automoveis token={sessao.token} />}
      {aba === "pedidos" && <Pedidos token={sessao.token} perfil={sessao.perfil} />}
    </div>
  );
}