# coding=utf-8
from tests.shared import Shared


class TestTypeEnvironment(Shared):
    def test_single_field(self):
        input_string = """
        form taxOfficeExample {
            "Did you sell a house in 2010?" hasSoldHouse: boolean
        }
        """
        form_node = self.acquire_ast(input_string)
        type_dict = self.acquire_types(form_node)
        self.assertEqual(len(type_dict), 1, "There should be exactly 1 identifier")
        self.assertEqual('boolean', type_dict['hasSoldHouse'].var_type, "hasSoldHouse should have type boolean")

    def test_money_field(self):
        input_string = """
        form taxOfficeExample {
            "How much money did you sell your house for?" sellAmount: money
        }
        """
        form_node = self.acquire_ast(input_string)
        type_dict = self.acquire_types(form_node)
        self.assertEqual(len(type_dict), 1, "There should be exactly 1 identifier")
        self.assertEqual('money', type_dict['sellAmount'].var_type, "sellAmount should have type money")

    def test_integer_field(self):
        input_string = """
        form taxOfficeExample {
            "How much years ago did you sell your house?" sellYear: integer
        }
        """
        form_node = self.acquire_ast(input_string)
        type_dict = self.acquire_types(form_node)
        self.assertEqual(len(type_dict), 1, "There should be exactly 1 identifier")
        self.assertEqual('integer', type_dict['sellYear'].var_type, "sellYear should have type money")

    def test_integer_in_if_field(self):
        input_string = """
        form taxOfficeExample {
            if(test) {
                "How much years ago did you sell your house?" sellYear: integer
            }
        }
        """
        form_node = self.acquire_ast(input_string)
        type_dict = self.acquire_types(form_node)
        self.assertEqual(len(type_dict), 1, "There should be exactly 1 identifier")
        self.assertEqual('integer', type_dict['sellYear'].var_type, "sellYear should have type money")

    def test_integer_in_if_else_field(self):
        input_string = """
        form taxOfficeExample {
            if(test) {
                "How much years ago did you sell your house?" sellYear: integer
            }
            else {
                "How much years ago did you buy your house?" buyYear: integer
            }
        }
        """
        form_node = self.acquire_ast(input_string)
        type_dict = self.acquire_types(form_node)
        self.assertEqual(len(type_dict), 2, "There should be exactly 2 identifiers")
        self.assertEqual('integer', type_dict['sellYear'].var_type, "sellYear should have type money")
        self.assertEqual('integer', type_dict['buyYear'].var_type, "buyYear should have type money")
