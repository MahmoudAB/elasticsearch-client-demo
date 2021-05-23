package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Component
class DemoElasticSearch(private val demoElasticSearchRepository: DemoElasticSearchRepository){

	@PostConstruct
	fun letsTestItOut(){
		val customer = Customer(UUID.randomUUID(), "Buddy Bob")
		demoElasticSearchRepository.save(customer)
		val ourBestCustomer = demoElasticSearchRepository.findByName(customer.name)
		println("OUR BEST CUSTOMER ==> ${ourBestCustomer.name}")
	}
}

@Document(indexName = "customer")
data class Customer(@Id val id: UUID, val name: String)

@Repository
interface DemoElasticSearchRepository: ElasticsearchRepository<Customer, UUID> {
	fun findByName(name: String): Customer
}
