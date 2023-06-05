package main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.DaoCliente;
import dao.DaoSoftwareLicenca;
import entidades.SoftwareLicenca;
import entidades.Cliente;

public class Programa {

	private static DaoCliente daoCliente = new DaoCliente();
	private static DaoSoftwareLicenca daoLicenca = new DaoSoftwareLicenca(daoCliente);

	public static void main(String[] args) throws SQLException {
	    try (Scanner scanner = new Scanner(System.in)) {
	        int opcao;
	        boolean exibirMenu = true;

	        do {
	            if (exibirMenu) {
	                exibirMenu();
	            }

	            if (scanner.hasNextLine()) {
	                opcao = Integer.parseInt(scanner.nextLine());
	            } else {
	                opcao = 0;
	                System.out.println("Bye...");
	                break;
	            }

	            switch (opcao) {
	                case 1: {
	                    cadastrarCliente();
	                    exibirMenu = false;
	                    break;
	                }
	                case 2: {
	                    cadastrarLicenca();
	                    exibirMenu = false;
	                    break;
	                }
	                case 3: {
	                    listarClientes();
	                    exibirMenu = true;
	                    break;
	                }
	                case 4: {
	                    listarLicencas();
	                    exibirMenu = true;
	                    break;
	                }
	                case 5: {
	                    excluirClienteELicencas();
	                    exibirMenu = false;
	                    break;
	                }
	                case 6: {
	                    atualizarCliente();
	                    exibirMenu = false;
	                    break;
	                }
	                case 7: {
	                    atualizarLicenca();
	                    exibirMenu = false;
	                    break;
	                }
	                case 0: {
	                    System.out.println("Bye...");
	                    break;
	                }
	                default:
	                    System.out.println("Opção inválida!");
	                    break;
	            }
	        } while (opcao != 0);
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	}

	public static void exibirMenu() {
	    System.out.println("Digite:");
	    System.out.println("1 - Para cadastrar cliente");
	    System.out.println("2 - Para cadastrar licenca");
	    System.out.println("3 - Para listar clientes");
	    System.out.println("4 - Para listar licencas");
	    System.out.println("5 - Para excluir cliente e suas licencas");
	    System.out.println("6 - Para atualizar cliente");
	    System.out.println("7 - Para atualizar licenca");
	    System.out.println("0 - Sair");
	}

	public static void cadastrarCliente() {
		System.out.println("##### Cadastrando Cliente #####");

		Cliente cliente = new Cliente();

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o nome do cliente:");
			cliente.setNomeCliente(scanner.nextLine());

			System.out.println("Digite o email do cliente:");
			cliente.setEmailCliente(scanner.nextLine());

			System.out.println("Digite o CPF do cliente:");
			cliente.setCpf(scanner.nextLine());
		}

		try {
			daoCliente.cadastrar(cliente);
			System.out.println("Cliente cadastrado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
		}
	}

	public static void cadastrarLicenca() throws SQLException {
		System.out.println("##### Cadastrando Licença #####");

		SoftwareLicenca licenca = new SoftwareLicenca();

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o ID do cliente:");
			int idCliente = Integer.parseInt(scanner.nextLine());

			Cliente cliente = daoCliente.buscarCliente(idCliente);
			if (cliente == null) {
				System.out.println("Cliente nao encontrado.");
				return;
			}

			System.out.println("Digite o nome do software:");
			licenca.setNomeSoftware(scanner.nextLine());

			licenca.setCliente(cliente);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}

		try {
			daoLicenca.cadastrar(licenca);
			System.out.println("Licença cadastrada com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar licença: " + e.getMessage());
		}
	}

	public static void listarClientes() throws SQLException {
		System.out.println("##### Listando Clientes #####");

		List<Cliente> clientes = daoCliente.listarClientes();

		if (clientes.isEmpty()) {
			System.out.println("Nao ha clientes cadastrados.");
			return;
		}

		for (Cliente cliente : clientes) {
			System.out.println("ID: " + cliente.getId());
			System.out.println("Nome: " + cliente.getNomeCliente());
			System.out.println("Email: " + cliente.getEmailCliente());
			System.out.println("CPF: " + cliente.getCpf());
			System.out.println();
		}
	}

	public static void listarLicencas() throws SQLException {
	    System.out.println("##### Listando Licencas #####");

	    List<SoftwareLicenca> licencas = daoLicenca.listarLicencas();

	    if (licencas.isEmpty()) {
	        System.out.println("Nao ha licencas cadastradas.");
	        return;
	    }

	    for (SoftwareLicenca licenca : licencas) {
	        System.out.println("ID: " + licenca.getId());
	        System.out.println("Nome do Software: " + licenca.getNomeSoftware());

	        Cliente cliente = licenca.getCliente();
	        if (cliente != null) {
	            System.out.println("Cliente ID: " + cliente.getId());
	            System.out.println("Email Cliente: " + cliente.getEmailCliente());
	        } else {
	            System.out.println("Cliente nao encontrado.");
	        }

	        System.out.println();
	    }
	}



	public static void excluirClienteELicencas() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Digite o ID do cliente a ser excluido: ");
			int idCliente = Integer.parseInt(scanner.nextLine());

			try {
				daoLicenca.excluirPorCliente(idCliente);
				daoCliente.excluir(idCliente);
				System.out.println("Cliente e suas licencas associadas excluidos com sucesso.");
			} catch (SQLException e) {
				System.out.println("Erro ao excluir cliente e suas licencas associadas: " + e.getMessage());
			}
		} catch (NumberFormatException e) {
			System.out.println("ID invalido. Certifique-se de inserir um numero inteiro.");
		}
	}
	
	public static void atualizarCliente() throws SQLException {
	    System.out.println("##### Atualizando Cliente #####");

	    try (Scanner scanner = new Scanner(System.in)) {
	        System.out.println("Digite o ID do cliente a ser atualizado:");
	        int idCliente = Integer.parseInt(scanner.nextLine());

	        Cliente cliente = daoCliente.buscarCliente(idCliente);
	        if (cliente == null) {
	            System.out.println("Cliente nao encontrado.");
	            return;
	        }

	        System.out.println("Digite o novo nome do cliente:");
	        cliente.setNomeCliente(scanner.nextLine());

	        System.out.println("Digite o novo email do cliente:");
	        cliente.setEmailCliente(scanner.nextLine());

	        System.out.println("Digite o novo CPF do cliente:");
	        cliente.setCpf(scanner.nextLine());

	        try {
	            daoCliente.atualizar(cliente);
	            System.out.println("Cliente atualizado com sucesso!");
	        } catch (SQLException e) {
	            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("ID inválido. Certifique-se de inserir um numero inteiro.");
	    }
	}

	public static void atualizarLicenca() throws SQLException {
	    System.out.println("##### Atualizando Licenca #####");

	    try (Scanner scanner = new Scanner(System.in)) {
	        System.out.println("Digite o ID da licenca a ser atualizada:");
	        int idLicenca = Integer.parseInt(scanner.nextLine());

	        SoftwareLicenca licenca = daoLicenca.buscarLicenca(idLicenca);
	        if (licenca == null) {
	            System.out.println("Licenca nao encontrada.");
	            return;
	        }

	        System.out.println("Digite o novo nome do software:");
	        licenca.setNomeSoftware(scanner.nextLine());

	        try {
	            daoLicenca.atualizar(licenca);
	            System.out.println("Licenca atualizada com sucesso!");
	        } catch (SQLException e) {
	            System.out.println("Erro ao atualizar licenca: " + e.getMessage());
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("ID invalido. Certifique-se de inserir um numero inteiro.");
	    }
	}
}
